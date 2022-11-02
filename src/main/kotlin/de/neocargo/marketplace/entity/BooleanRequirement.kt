package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "scopes"
)
data class BooleanRequirement(
    val scopes: Scopes,
)
