package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
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
@CrossOrigin(origins = arrayOf("http:://localhost:3000"), allowCredentials = "true")
@RequestMapping("${Router.API_PATH}/biddings")
class BiddingController(
    private val biddingRepository: BiddingRepository
) {
    @PostMapping
    @PreAuthorize("#user.id != null")
    fun createBidding(@AuthenticationPrincipal user: User, @RequestBody request: Shipment): ResponseEntity<Bidding> {
        val bidding = biddingRepository.save(
            Bidding(
                userId = user.id.toString(),
                shipment = request
            )
        )
        val responseEntity = ResponseEntity(bidding, HttpStatus.CREATED)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/{id}")
    fun findBiddingById(@PathVariable("id")id: String): ResponseEntity<Bidding> {
        val bidding = biddingRepository.findByBiddingId(id)
        val responseHeaders = HttpHeaders()
        responseHeaders[HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN] = "*"
        val responseEntity = ResponseEntity(bidding, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/")
    @PreAuthorize("#user.id != null")
    fun findAllBiddingsByUserId(@AuthenticationPrincipal user: User): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAllBiddingsByUserId(user.id.toString())
        val responseHeaders = HttpHeaders()
        responseHeaders[HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN] = "*"
        val responseEntity = ResponseEntity(biddings, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }
}
