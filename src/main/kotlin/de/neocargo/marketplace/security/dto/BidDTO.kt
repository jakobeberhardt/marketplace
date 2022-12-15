package de.neocargo.marketplace.security.dto

data class BidDTO(
    var biddingId: String,
    var userId: String,
    var value: Long,
    var currency: String)
{

}