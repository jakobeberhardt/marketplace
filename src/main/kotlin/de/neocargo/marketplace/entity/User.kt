package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@JsonPropertyOrder(
    "id",
    "username",
    "password"
)
@Document(collection = "users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),
    private val username: String,
    private var password: String,
) : UserDetails {


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return Collections.emptyList()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    fun setPassword(password : String) {
        this.password = password
    }
}

