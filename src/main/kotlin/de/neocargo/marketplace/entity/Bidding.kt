package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@JsonPropertyOrder(
        "id",
        "shipment",
        )
@Document
data class Bidding (
        @Id
        val id : Long,
        val shipment : Shipment,
)