package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "id",
    "tmsReference",
    "position",
    "label",
    "pickup",
    "pickupReference",
    "delivery",
    "deliveryReference",
    "totalItemCount",
    "totalWeight",
    "totalVolume",
    "totalLoadMeters",
    "internalInfo",
    "requirements",
)
data class Shipment(
    val id : Long,
    val tmsReference : String?,
    val position : Int,
    val label : String?,
    val pickup : Visit,
    val pickupReference : String?,
    val delivery : Visit,
    val deliveryReference : String?,
    val totalItemCount : Int? = 0,
    val totalWeight : Double? = 0.0,
    val totalVolume : Double? = 0.0,
    val totalLoadMeters : Double? = 0.0,
    val internalInfo : String?,
    val requirements : ArrayList<Requirement> = ArrayList<Requirement>(),
)