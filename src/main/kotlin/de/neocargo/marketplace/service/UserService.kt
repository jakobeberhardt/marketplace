@file:Suppress("UNREACHABLE_CODE")

package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository
    ) {

    fun getWhitelist(userId: String): ArrayList<String> = userRepository.findById(userId).whitelist as ArrayList<String>

    fun addUserToWhitelist(userId: String, newUserId: String) : User {
        val updatedUser: User = userRepository.findById(userId)
        if (!(updatedUser.whitelist.contains(newUserId))) {
            updatedUser.whitelist.add(newUserId)
            userRepository.save(updatedUser)
        }
        return updatedUser
    }

    fun deleteUserFromWhitelist(userId: String, removeUserId: String): User {
        val updatedUser : User = userRepository.findById(userId)
        if (updatedUser.whitelist.contains(removeUserId)) {
            updatedUser.whitelist.remove(removeUserId)
            userRepository.save(updatedUser)
        }
        return updatedUser
    }
}
