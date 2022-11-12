package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.Date

@JsonPropertyOrder(
    "startTime",
    "endTime",
)
data class AllowedTimeWindow(
    val startTime: Date,
    val endTime: Date,
)
