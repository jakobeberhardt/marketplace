package de.neocargo.marketplace.config.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
class SecurityConfig {
    @Throws(Exception::class)
    public fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable().antMatcher("/**").authorizeRequests()
            .antMatchers("/", "/index").authenticated()
            .anyRequest().authenticated()
            .and()
            // Permits all logins at the moment
            .oauth2Login().permitAll()
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutSuccessUrl("/")
    }
}