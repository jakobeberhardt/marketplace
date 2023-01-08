package de.neocargo.marketplace.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Document
data class User(
    var whitelist: MutableList<String> = mutableListOf(),
    var publishedBiddings : MutableSet<String> = mutableSetOf(),
    var assignedBiddings : MutableSet<String> = mutableSetOf(),
    var bids : MutableSet<Bid> = mutableSetOf()
) : UserDetails {
    @Id
    var id: String? = (Random().nextInt(900000) + 100000).toString()
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
}
