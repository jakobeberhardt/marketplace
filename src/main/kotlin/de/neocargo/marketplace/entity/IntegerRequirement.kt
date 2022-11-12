package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "scopes",
    "params",
)
data class IntegerRequirement(
    val scopes: Scopes,
    val params: String,
)
