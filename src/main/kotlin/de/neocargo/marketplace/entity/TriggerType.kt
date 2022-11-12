package de.neocargo.marketplace.entity

enum class TriggerType(val type: String) {
    FIXED_DATETIME("FIXED_DATETIME"),
    ARRIVAL_OFFSET("ARRIVAL_OFFSET"),
    GEOFENCE("GEOFENCE"),
}
