package de.neocargo.marketplace.security

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

private val logger = KotlinLogging.logger { }

@Component
class KeyUtils (
    @Autowired
    private val environment: Environment,
) {
    @Value("\${access-token.private}")
    private val accessTokenPrivateKeyPath: String? = null

    @Value("\${access-token.public}")
    private val accessTokenPublicKeyPath: String? = null

    @Value("\${refresh-token.private}")
    private val refreshTokenPrivateKeyPath: String? = null

    @Value("\${refresh-token.public}")
    private val refreshTokenPublicKeyPath: String? = null
    private var _accessTokenKeyPair: KeyPair? = null
    private var _refreshTokenKeyPair: KeyPair? = null
    private val accessTokenKeyPair: KeyPair?
        get() {
            if (Objects.isNull(_accessTokenKeyPair)) {
                _accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath)
            }
            return _accessTokenKeyPair
        }
    private val refreshTokenKeyPair: KeyPair?
        get() {
            if (Objects.isNull(_refreshTokenKeyPair)) {
                _refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath)
            }
            return _refreshTokenKeyPair
        }

    private fun getKeyPair(publicKeyPath: String?, privateKeyPath: String?): KeyPair {
        val keyPair: KeyPair
        val publicKeyFile = File(publicKeyPath)
        val privateKeyFile = File(privateKeyPath)
        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            logger.info("loading keys from file: {}, {}", publicKeyPath, privateKeyPath)
            return try {
                val keyFactory = KeyFactory.getInstance("RSA")
                val publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath())
                val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
                val publicKey = keyFactory.generatePublic(publicKeySpec)
                val privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath())
                val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
                val privateKey = keyFactory.generatePrivate(privateKeySpec)
                keyPair = KeyPair(publicKey, privateKey)
                keyPair
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            } catch (e: IOException) {
                throw RuntimeException(e)
            } catch (e: InvalidKeySpecException) {
                throw RuntimeException(e)
            }
        } else {
            // Abort if keys are not present in production
            if (Arrays.stream(environment.activeProfiles).anyMatch { s: String -> s == "prod" }) {
                throw RuntimeException("public and private keys don't exist")
            }
        }
        val directory = File("access-refresh-token-keys")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        try {
            logger.info("Generating new public and private keys: {}, {}", publicKeyPath, privateKeyPath)
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPair = keyPairGenerator.generateKeyPair()
            FileOutputStream(publicKeyPath).use { fos ->
                val keySpec = X509EncodedKeySpec(keyPair.public.encoded)
                fos.write(keySpec.encoded)
            }
            FileOutputStream(privateKeyPath).use { fos ->
                val keySpec = PKCS8EncodedKeySpec(keyPair.private.encoded)
                fos.write(keySpec.encoded)
            }
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return keyPair
    }

    val accessTokenPublicKey: RSAPublicKey
        get() = accessTokenKeyPair!!.public as RSAPublicKey
    val accessTokenPrivateKey: RSAPrivateKey
        get() = accessTokenKeyPair!!.private as RSAPrivateKey
    val refreshTokenPublicKey: RSAPublicKey
        get() = refreshTokenKeyPair!!.public as RSAPublicKey
    val refreshTokenPrivateKey: RSAPrivateKey
        get() = refreshTokenKeyPair!!.private as RSAPrivateKey
}