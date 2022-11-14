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
        // TODO: Add secondary constructor for post-signup User objects
        val user = User()
        user.setId(jwt.subject)

        return UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList())
    }
}