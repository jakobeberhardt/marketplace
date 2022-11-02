package de.neocargo.marketplace.entity

data class Customer(
    val id: Long,
    val name: String?,
    val tmsReference: String?,
    val vatId: String?,
    val address: CustomerAddress?,
)
