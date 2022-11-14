package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.security.dto.LoginDTO
import de.neocargo.marketplace.security.dto.SignupDTO
import de.neocargo.marketplace.security.dto.TokenDTO
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.security.TokenGenerator
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.*
import java.util.*

private val logger = KotlinLogging.logger { }

@CrossOrigin
@RestController
@RequestMapping("${Router.API_PATH}/auth")
class AuthController(
    @Autowired
    private val userDetailsManager: UserDetailsManager,
    @Autowired
    private val tokenGenerator: TokenGenerator,
    @Autowired
    private val daoAuthenticationProvider: DaoAuthenticationProvider,
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private val refreshTokenAuthProvider: JwtAuthenticationProvider,
) {
    @PostMapping("/register")
    fun register(@RequestBody signupDTO: SignupDTO): ResponseEntity<TokenDTO> {
        val user = User()
        user.setUsername(signupDTO.username)
        user.setPassword(signupDTO.password)
        userDetailsManager.createUser(user)
        val authentication: Authentication =
            UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.password, Collections.emptyList())
        val responseEntity = ResponseEntity(tokenGenerator.createToken(authentication), HttpStatus.OK)
        logger.info { "${responseEntity.statusCode} Issued credentials for user ${user.username.toString()}" }
        return responseEntity
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<TokenDTO> {
        val authentication = daoAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.username, loginDTO.password))
        val responseEntity =  ResponseEntity(tokenGenerator.createToken(authentication), HttpStatus.OK)
        logger.info { "${responseEntity.statusCode} Issued credentials on login for user ${loginDTO.username}" }
        return responseEntity
    }

    @PostMapping("/token")
    fun token(@RequestBody tokenDTO: TokenDTO): ResponseEntity<TokenDTO> {
        val authentication =
            refreshTokenAuthProvider.authenticate(BearerTokenAuthenticationToken(tokenDTO.refreshToken))
          /*
           * e.g. for checking if userId is present in db and/or not revoked or expired
           * val jwt = authentication.credentials as Jwt
           */
        val responseEntity = ResponseEntity(tokenGenerator.createToken(authentication), HttpStatus.OK)
        logger.info { "${responseEntity.statusCode} Issued refreshed token" }
        return responseEntity
    }
}