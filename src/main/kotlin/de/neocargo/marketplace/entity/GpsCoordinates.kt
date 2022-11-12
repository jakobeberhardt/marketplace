package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "latidute",
    "longtidude",
)
data class GpsCoordinates(
    val latitude : Double,
    val longitude : Double,
)
