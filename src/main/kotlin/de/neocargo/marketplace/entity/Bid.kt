package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@JsonPropertyOrder(
    "id",
    "userId",
)
@Document(collection = "bids")
data class Bid(
    @Id
    val id : String = UUID.randomUUID().toString(),
    val userId : Long = 1,
)