package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.repository.BiddingRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/biddings")
class BiddingController(
    private val biddingRepository: BiddingRepository
) {
    @PostMapping
    fun createBidding(@RequestBody request: Shipment): ResponseEntity<Bidding> {
        val bidding = biddingRepository.save(
            Bidding(
                // to be updated once userId is transferred in ShipmentTO
                // userId = request.userId,
                // shipment = request.shipment
                shipment = request
            )
        )
        return ResponseEntity(bidding, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllBiddings(): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAll()
        return ResponseEntity(biddings, HttpStatus.ACCEPTED)
    }

    @GetMapping("/{id}")
    fun findBiddingById(@PathVariable("id")id : String): ResponseEntity<Bidding> {
        val bidding = biddingRepository.findByBiddingId(id)
        return ResponseEntity(bidding, HttpStatus.ACCEPTED)
    }

    @GetMapping("/user/{userId}")
    fun findAllBiddingsByUserId(@PathVariable("userId")userId : Long): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAllBiddingsByUserId(userId)
        return ResponseEntity(biddings, HttpStatus.ACCEPTED)
    }
}