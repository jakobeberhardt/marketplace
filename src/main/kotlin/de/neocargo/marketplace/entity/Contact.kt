package de.neocargo.marketplace.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    "id",
    "tmsReference",
    "customerId",
    "name",
    "phone",
    "mail",
)
data class Contact(
    val id: Long,
    val tmsReference: String?,
    val customerId: Long?,
    val name: String?,
    val phone: String?,
    val mail: String?,
)
