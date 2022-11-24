package de.neocargo.marketplace.security.dto

import de.neocargo.marketplace.entity.User

data class WhitelistDTO(
    var id : String?,
    var whitelist : List<String>
){

    companion object {
        fun from(user: User): WhitelistDTO {
            return WhitelistDTO(user.id, user.whitelist)
        }
    }
}
