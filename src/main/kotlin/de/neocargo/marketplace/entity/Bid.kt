package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@JsonPropertyOrder(
    "id",
    "biddingId",
    "userId",
    "value",
    "currency",
    "status"
)
@Document(collection = "bids")
data class Bid(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val biddingId : String,
    val userId: String,
    val value: Long,
    val currency: String,
    var status : Status = Status.ACTIVE
    )
