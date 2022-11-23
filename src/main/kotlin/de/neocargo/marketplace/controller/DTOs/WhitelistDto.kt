package de.neocargo.marketplace.controller.DTOs

import de.neocargo.marketplace.entity.User

data class WhitelistDto(
    var id : String?,
    var whitelist : List<String>
){

companion object {
    fun toWhitelistDto(user: User): WhitelistDto = WhitelistDto(id = user.id, whitelist = user.whitelist)
    }
}


