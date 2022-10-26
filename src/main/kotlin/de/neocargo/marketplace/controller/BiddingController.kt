package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.repository.BiddingRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/biddings")
class BiddingController(
    private val biddingRepository: BiddingRepository
) {
    @PostMapping
    fun createBidding(@RequestBody request: Bidding): ResponseEntity<Bidding> {
        val bidding = biddingRepository.save(
            Bidding(
            id = request.id,
            shipment = request.shipment
        )
        )
        return ResponseEntity(bidding, HttpStatus.CREATED)
    }

}