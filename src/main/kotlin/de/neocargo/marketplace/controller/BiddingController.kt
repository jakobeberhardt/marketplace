package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.repository.BiddingRepository
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@CrossOrigin
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

        val responseEntity = ResponseEntity(bidding, HttpStatus.CREATED)
        logger.info(responseEntity.toString())
        return responseEntity
    }

    @GetMapping
    fun getAllBiddings(): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAll()
        val responseEntity = ResponseEntity(biddings, HttpStatus.ACCEPTED)
        logger.info(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/{id}")
    fun findBiddingById(@PathVariable("id")id: String): ResponseEntity<Bidding> {
        val bidding = biddingRepository.findByBiddingId(id)
        val responseEntity = ResponseEntity(bidding, HttpStatus.ACCEPTED)
        logger.info(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/user/{userId}")
    fun findAllBiddingsByUserId(@PathVariable("userId")userId: Long): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAllBiddingsByUserId(userId)
        val responseEntity = ResponseEntity(biddings, HttpStatus.ACCEPTED)
        logger.info(responseEntity.toString())
        return responseEntity
    }
}
