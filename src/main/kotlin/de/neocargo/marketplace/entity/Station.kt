package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "id",
    "tmsReference",
    "name",
    "address",
    "gpsCoords",
    "customer",
    "mainContact",
)
data class Station(
    val id: Long,
    val tmsReference: String?,
    val name: String?,
    val address: StationAddress?,
    val gpsCoords: GpsCoordinates?,
    val customer: Customer?,
    val mainContact: Contact?,
)
