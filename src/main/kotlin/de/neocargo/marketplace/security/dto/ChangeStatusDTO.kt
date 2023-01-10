package de.neocargo.marketplace.security.dto

import de.neocargo.marketplace.entity.Status

data class ChangeStatusDTO(
    val biddingId: String,
    val newStatus: String,
)
