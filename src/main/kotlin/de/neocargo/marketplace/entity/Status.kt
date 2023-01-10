package de.neocargo.marketplace.entity

enum class Status(val value: String) {
    ACTIVE("active"),
    PAUSED("paused"),
    FINISHED("finished"),;

    companion object {
        infix fun from(value: String): Status? = Status.values().firstOrNull { it.value == value }
    }
}