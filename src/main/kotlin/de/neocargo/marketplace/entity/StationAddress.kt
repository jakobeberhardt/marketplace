package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "name",
    "building",
    "gate",
    "street",
    "zipcode",
    "city",
    "country",
)
data class StationAddress(
    val name: String?,
    val building: String?,
    val gate: String?,
    val street: String?,
    val zipcode: String?,
    val city: String?,
    val country: String?,
)
