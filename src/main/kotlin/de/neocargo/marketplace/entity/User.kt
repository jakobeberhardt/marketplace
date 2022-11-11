package de.neocargo.marketplace.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Document(collection = "users")
class User(private val username: String, private var password: String) : UserDetails {
    @Id
    val id: String = UUID.randomUUID().toString()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return this.isAccountNonExpired()
    }

    override fun isAccountNonLocked(): Boolean {
        return this.isAccountNonLocked()
    }

    override fun isCredentialsNonExpired(): Boolean {
        return this.isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return this.isEnabled
    }

    fun setPassword(password: String) {
        this.password = password
    }
}

