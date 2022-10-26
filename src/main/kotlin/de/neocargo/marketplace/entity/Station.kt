package de.neocargo.marketplace.entity

data class Station(
    val id : Long,
    val tmsReference : String?,
    val name : String?,
    val address : StationAddress?,
    val gpsCoords : GpsCoordinates?,
    val customer : Customer?,
    val mainContact : Contact?,
)
