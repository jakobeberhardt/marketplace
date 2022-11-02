package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "station",
    "contact",
    "plannedServiceDuration",
    "avis",
    "allowedTimeWindows",
    "timeWindowBookingUrl",
)
data class Visit(
    val station: Station,
    val contact: String,
    val plannedServiceDuration: Int?,
    val avis: List<Avis>,
    val allowedTimeWindows: List<AllowedTimeWindow>,
    val timeWindowBookingUrl: String,
)
