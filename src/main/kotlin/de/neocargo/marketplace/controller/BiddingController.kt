package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
@RequestMapping("${Router.API_PATH}/biddings")
class BiddingController(
    private val biddingRepository: BiddingRepository
) {
    // TODO: Evaluate proper way to insure that user is logged in and has userId property set
    @PostMapping
    @PreAuthorize("#user.id != null")
    fun createBidding(@AuthenticationPrincipal user: User, @RequestBody request: Shipment): ResponseEntity<Bidding> {
        val bidding = biddingRepository.save(
            Bidding(
                userId = user.getId().toString(),
                shipment = request
            )
        )

        val responseEntity = ResponseEntity(bidding, HttpStatus.CREATED)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping
    fun getAllBiddings(): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAll()
        val responseEntity = ResponseEntity(biddings, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/{id}")
    fun findBiddingById(@PathVariable("id")id: String): ResponseEntity<Bidding> {
        val bidding = biddingRepository.findByBiddingId(id)
        val responseEntity = ResponseEntity(bidding, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("#user.id == #userId")
    fun findAllBiddingsByUserId(@AuthenticationPrincipal user: User, @PathVariable userId: String): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAllBiddingsByUserId(user.getId().toString())
        val responseEntity = ResponseEntity(biddings, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }
}
