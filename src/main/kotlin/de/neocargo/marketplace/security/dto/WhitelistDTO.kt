package de.neocargo.marketplace.security.dto

import de.neocargo.marketplace.entity.User

data class WhitelistDTO(
    var whitelist : MutableList<String>
){

    companion object {
        fun from(user: User): WhitelistDTO {
            return WhitelistDTO(user.whitelist)
        }
    }
}
