package de.neocargo.marketplace.entity

enum class Scopes(val type: String) {
    PICKUP("PICKUP"),
    ENROUTE("ENROUTE"),
    DELIVERY("DELIVERY")
}