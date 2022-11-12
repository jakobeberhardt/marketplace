package de.neocargo.marketplace.entity

import lombok.Data
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Document
@Data
@RequiredArgsConstructor
@NoArgsConstructor
class User : UserDetails {
    @Id
    private var id: String? = null
    private var username: String? = null
    private var password: String? = null
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return Collections.emptyList()
    }

    override fun getPassword(): String? {
        return this.password
    }

    override fun getUsername(): String? {
        return this.username
    }

    fun getId(): String? {
        return this.id
    }

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

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setId(id: String) {
        this.id = id
    }
}