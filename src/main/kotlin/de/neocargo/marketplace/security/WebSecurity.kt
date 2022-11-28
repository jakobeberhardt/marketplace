package de.neocargo.marketplace.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import de.neocargo.marketplace.config.Router
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurity (
    @Autowired
    private val jwtToUserConverter: JwtToUserConverter,
    @Autowired
    private val keyUtils: KeyUtils,
    @Autowired
    @Lazy val passwordEncoder: PasswordEncoder,
    @Autowired
    @Lazy val userDetailsManager: UserDetailsManager,
        ) {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
            }
        }
    }
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests(
                Customizer { authorize ->
                    authorize
                        .antMatchers("${Router.API_PATH}/auth/*").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                }
            )
            .csrf().disable()
            .httpBasic().disable()
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> ->
                oauth2.jwt(
                    Customizer { jwt ->
                        jwt.jwtAuthenticationConverter(
                            jwtToUserConverter
                        )
                    })
            }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity?> ->
                exceptions
                    .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(BearerTokenAccessDeniedHandler())
            }
            .cors()
        return http.build()
    }

    @Bean
    @Primary
    fun jwtAccessTokenDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyUtils.accessTokenPublicKey).build()
    }

    @Bean
    @Primary
    fun jwtAccessTokenEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(keyUtils.accessTokenPublicKey)
            .privateKey(keyUtils.accessTokenPrivateKey)
            .build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    fun jwtRefreshTokenDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyUtils.refreshTokenPublicKey).build()
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    fun jwtRefreshTokenEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(keyUtils.refreshTokenPublicKey)
            .privateKey(keyUtils.refreshTokenPrivateKey)
            .build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    @Qualifier("jwtRefreshTokenAuthProvider")
    fun jwtRefreshTokenAuthProvider(): JwtAuthenticationProvider {
        val provider = JwtAuthenticationProvider(jwtRefreshTokenDecoder())
        provider.setJwtAuthenticationConverter(jwtToUserConverter)
        return provider
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(userDetailsManager)
        return provider
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun header(): HttpHeaders {
        return HttpHeaders()
    }
}