package de.neocargo.marketplace.security

import de.neocargo.marketplace.dto.TokenDTO
import de.neocargo.marketplace.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.text.MessageFormat
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class TokenGenerator {
    @Autowired
    var accessTokenEncoder: JwtEncoder? = null

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    var refreshTokenEncoder: JwtEncoder? = null


    private fun createAccessToken(authentication: Authentication): String? {
        val user: User = authentication.principal as User
        val now = Instant.now()
        val claimsSet = JwtClaimsSet.builder()
            .issuer("myApp")
            .issuedAt(now)
            .expiresAt(now.plus(5, ChronoUnit.MINUTES))
            .subject(user.id)
            .build()
        return accessTokenEncoder!!.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    private fun createRefreshToken(authentication: Authentication):     String? {
        val user: User = authentication.principal as User
        val now = Instant.now()
        val claimsSet = JwtClaimsSet.builder()
            .issuer("myApp")
            .issuedAt(now)
            .expiresAt(now.plus(30, ChronoUnit.DAYS))
            .subject(user.id)
            .build()
        return refreshTokenEncoder!!.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    fun  createToken(authentication :Authentication) : TokenDTO {
        if (!(authentication.principal is User)) {
            throw BadCredentialsException(
                    MessageFormat.format("principal {0} is not of User type", authentication.getPrincipal()::class)
            );
        }

        val user: User = authentication.principal as User
        val tokenDTO = TokenDTO(userId = user.id, accessToken = createAccessToken(authentication), null)

        return tokenDTO;
    }

}