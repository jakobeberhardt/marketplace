@file:Suppress("UNREACHABLE_CODE")

package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository
    ) {

    fun getWhitelist(userId: String): User = userRepository.findById(userId)

    fun addUserToWhitelist(userId: String, newUserId: String) : User {
        val updatedUser: User = userRepository.findById(userId)
        if (updatedUser.whitelist.isEmpty()) {
            updatedUser.whitelist[1] = newUserId
        }
        else {
            updatedUser.whitelist[updatedUser.whitelist.keys.last() + 1] = newUserId
        }
        userRepository.save(updatedUser)
        return updatedUser
    }

    fun deleteUserFromWhitelist(userId: String, removeUserId: String): User {
        val updatedUser : User = userRepository.findById(userId)
        if (updatedUser.whitelist.containsValue(removeUserId)){
            updatedUser.whitelist.values.remove(removeUserId)
            userRepository.save(updatedUser)
        }
        return updatedUser
    }
}
