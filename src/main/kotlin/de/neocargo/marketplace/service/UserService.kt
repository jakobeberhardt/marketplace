@file:Suppress("UNREACHABLE_CODE")

package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    val userRepository: UserRepository
    ) {

    fun addUserToWhitelist(user: String, newUserId: String) : User {
        val updatedUser: User = userRepository.findById(user)
        updatedUser.whitelist.let {
            it.add(newUserId)
        }
        updatedUser.id.let {
            if (it != null) {
                return userRepository.save(updatedUser)
            }
        }
        return userRepository.findById(user)
    }}
