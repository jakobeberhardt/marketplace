import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurity {
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
                        .anyRequest().authenticated()
                        .and()
                        .oauth2Login()
                }
            )
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .cors()
        return http.build()
    }

    @Bean
    fun header(): HttpHeaders {
        return HttpHeaders()
    }
}