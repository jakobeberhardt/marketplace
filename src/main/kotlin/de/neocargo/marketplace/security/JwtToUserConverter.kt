package de.neocargo.marketplace.security


import de.neocargo.marketplace.entity.User
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtToUserConverter : Converter<Jwt, UsernamePasswordAuthenticationToken> {
    override fun convert(jwt: Jwt): UsernamePasswordAuthenticationToken {
        // Creates user with JWT subject ID
        val user = User(username = jwt.subject, password =  "Passwd")
        return UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList())
    }
}