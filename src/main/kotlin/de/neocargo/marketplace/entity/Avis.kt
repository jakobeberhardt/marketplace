package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "triggerType",
    "avisType",
    "triggerOffset",
    "recipient",
)
data class Avis(
    val triggerType: TriggerType,
    val avisType: AvisType,
    val triggerOffset: String,
    val recipient: String,
)
