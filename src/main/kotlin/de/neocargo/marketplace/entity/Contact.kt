package de.neocargo.marketplace.entity

data class Contact(
    val id : Long,
    val tmsReference : String?,
    val customerId : Long?,
    val name : String?,
    val phone : String?,
    val mail : String?,
)
