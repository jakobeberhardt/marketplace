@file:Suppress("UNREACHABLE_CODE")

package de.neocargo.marketplace.service

import de.neocargo.marketplace.controller.DTOs.WhitelistDto
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import de.neocargo.marketplace.security.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    val userRepository: UserRepository) {

    fun addUserToWhiteList(user: String, newUserId: String) : User {
        val updatedUser: User = userRepository.findById(user)
        updatedUser.whitelist.let{
            it.add(newUserId)
        }
        updatedUser.id.let {
            if (it != null) {
                return userRepository.save(updatedUser)
            }
        }
        return userRepository.findById(user)


    fun deleteUserFromWhiteList(user: String, newUserId: String) : WhitelistDto {
        val updateUser: User = userRepository.findById(user)
        updateUser.whitelist.remove(newUserId)
        userRepository.save(updatedUser)
        val userDto = WhitelistDto.toWhitelistDto(updatedUser)
        return userDto
    }


    }}