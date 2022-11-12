package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Schema(description = "Model for a Bidding")
@JsonPropertyOrder(
    "id",
    "userId",
    "shipment",
    "bids",
)
@Document(collection = "biddings")
data class Bidding(
    @field:Schema(
        description = "Id for a Bidding",
        example = "00000000-0000-0000-0000-000000000001",
        type = "UUID",
    )
    @Id
    val id: String = UUID.randomUUID().toString(),
    @field:Schema(
        description = "Id for an user",
        example = "1",
        type = "Long",
        minimum = "0",
    )
    val userId: String,
    @field:Schema(
        description = "Shipment",
        type = "Shipment",
    )
    val shipment: Shipment,
    @field:Schema(
        description = "bids",
        type = "Bid[]",
    )
    var bids: ArrayList<Bid> = arrayListOf(),
)
