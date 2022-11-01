package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID


@JsonPropertyOrder(
        "id",
        "shipment",
)
@Document(collection = "biddings")
data class Contract (
        @Id
        val id : String = UUID.randomUUID().toString(),
        val userId : Long,
        val shipment : ShipmentTO,
)