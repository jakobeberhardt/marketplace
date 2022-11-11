package de.neocargo.marketplace.controller

import de.neocargo.marketplace.security.dto.LoginDTO
import de.neocargo.marketplace.security.dto.SignupDTO
import de.neocargo.marketplace.security.dto.TokenDTO
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.security.TokenGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    @Autowired
    private val userDetailsManager: UserDetailsManager,
    @Autowired
    private val tokenGenerator: TokenGenerator,
    @Autowired
    private val daoAuthenticationProvider: DaoAuthenticationProvider,
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private val refreshTokenAuthProvider: JwtAuthenticationProvider
) {
    @PostMapping("/register")
    fun register(@RequestBody signupDTO: SignupDTO): ResponseEntity<*> {
        val user = User(username = signupDTO.username, password = signupDTO.password)
        userDetailsManager!!.createUser(user)
        val authentication: Authentication =
            UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.password, Collections.emptyList())
        return ResponseEntity.ok(tokenGenerator.createToken(authentication))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<*> {
        val authentication = daoAuthenticationProvider!!.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(
                loginDTO.username,
                loginDTO.password
            )
        )
        return ResponseEntity.ok(tokenGenerator.createToken(authentication))
    }

    @PostMapping("/token")
    fun token(@RequestBody tokenDTO: TokenDTO): ResponseEntity<*> {
        val authentication =
            refreshTokenAuthProvider!!.authenticate(BearerTokenAuthenticationToken(tokenDTO.refreshToken))
        val jwt = authentication.credentials as Jwt
        // check if present in db and not revoked, etc
        return ResponseEntity.ok(tokenGenerator.createToken(authentication))
    }
}