package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@JsonPropertyOrder(
    "id",
    "userId",
    "shipment",
    "bids",
)
@Document(collection = "biddings")
data class Bidding(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val userId: Long = 1,
    val shipment: Shipment,
    var bids: ArrayList<Bid> = arrayListOf(),
)
