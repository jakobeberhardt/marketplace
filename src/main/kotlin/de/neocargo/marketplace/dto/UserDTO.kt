package de.neocargo.marketplace.dto

import de.neocargo.marketplace.entity.User
import lombok.Builder
import lombok.Data

@Builder
@Data
data class UserDTO (
    val id: String,
    val username: String,
) {

    companion object {
        fun from(user: User): UserDTO {
            return UserDTO(id = user.id, username = user.username)
        }
    }
}