package de.neocargo.marketplace.security.dto

import de.neocargo.marketplace.entity.User

data class UserDTO (
    val id: String?,
    val username: String?,
) {

    companion object {
        fun from(user: User): UserDTO = UserDTO(id = user.id, username = user.username)

    }
}