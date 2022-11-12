package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "scopes",
    "params",
)
data class EnumRequirement(
    val scopes: Scopes,
    val params: String?,
)
