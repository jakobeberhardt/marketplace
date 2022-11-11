package de.neocargo.marketplace.security.dto

class TokenDTO (
    val userId: String?,
    val accessToken: String?,
    var refreshToken: String?,
) {
    @JvmName("setRefreshToken1")
    fun setRefreshToken(refreshed: String){
        this.refreshToken = refreshed
    }
}