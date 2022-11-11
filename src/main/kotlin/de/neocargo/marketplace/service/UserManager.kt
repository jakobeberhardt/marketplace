package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

@Service
class UserManager (
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    val passwordEncoder: PasswordEncoder
) : UserDetailsManager {

    // TODO: Fix Casting from User to org.springframework.security.core.userdetails.User in entity User and frontend implementation
    override fun createUser(user: UserDetails) {
        (user as User).password = passwordEncoder.encode(user.password)
        userRepository.save(user as org.springframework.security.core.userdetails.User)
    }

    // TODO: Supressed for now
    override fun updateUser(user: UserDetails) {}
    override fun deleteUser(username: String) {}
    override fun changePassword(oldPassword: String, newPassword: String) {}
    override fun userExists(id: String): Boolean {
        return userRepository.existsById(id)
    }

    @Throws(Exception::class)
    fun loadUserById(id: String): UserDetails {
        return userRepository.findById(id)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findByUsername(username)
    }


}