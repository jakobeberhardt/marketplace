package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "name",
    "street",
    "zipcode",
    "city",
    "country",
)
data class CustomerAddress(
    val name: String?,
    val street: String?,
    val zipcode: String?,
    val city: String?,
    val country: String?,
)
