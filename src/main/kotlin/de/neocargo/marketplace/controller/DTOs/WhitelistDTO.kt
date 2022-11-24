package de.neocargo.marketplace.controller.DTOs

import de.neocargo.marketplace.entity.User

data class WhitelistDTO(
    var id : String?,
    var whitelist : List<String>
){

    companion object {
        fun toWhitelistDto(user: User): WhitelistDTO {
            return WhitelistDTO(user.id, user.whitelist)
        }
    }
}
