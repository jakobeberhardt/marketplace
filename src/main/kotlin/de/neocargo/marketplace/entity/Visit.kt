package de.neocargo.marketplace.entity

data class Visit(
    val station: Station,
    val contact: String,
    val plannedServiceDuration: Int?,
    val avis: List<Avis>,
    val allowedTimeWindows: List<AllowedTimeWindow>,
    val timeWindowBookingUrl: String,
)
