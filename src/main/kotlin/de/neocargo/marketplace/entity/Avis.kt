package de.neocargo.marketplace.entity

data class Avis(
    val triggerType : TriggerType,
    val avisType : AvisType,
    val triggerOffset : String,
    val recipient : String,
)
