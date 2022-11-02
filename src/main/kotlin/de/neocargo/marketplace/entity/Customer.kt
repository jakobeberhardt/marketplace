package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "id",
    "name",
    "tmsReference",
    "vatId",
    "address",
)
data class Customer(
    val id: Long,
    val name: String?,
    val tmsReference: String?,
    val vatId: String?,
    val address: CustomerAddress?,
)
