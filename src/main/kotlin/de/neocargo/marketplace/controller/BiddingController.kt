package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.entity.Visit
import de.neocargo.marketplace.repository.BiddingRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@CrossOrigin
@RequestMapping("${Router.API_PATH}/biddings")
class BiddingController(
    @Autowired
    private val biddingRepository: BiddingRepository,
/*    @Autowired
    private val responseHeaders: HttpHeaders*/
) {
    private fun prepareBiddingRequestBody(): Shipment = Shipment(
        1,
        "Test",
        1,
        "Test",
        Visit(
            null,
            "test",
            1,
            null,
            null,
            "Test"
        ),
        "Test",
        Visit(
            null,
            "test",
            1,
            null,
            null,
            "Test"
        ),
        "Test",
        1,
        1.1,
        1.1,
        1.1,
        "Test",
        null,
    )
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

    @GetMapping("/")
    @PreAuthorize("#user.id != null")
    fun findAllBiddingsByUserId(@AuthenticationPrincipal user: User): ResponseEntity<List<Bidding>> {
        val biddings = biddingRepository.findAllBiddingsByUserId(user.getId().toString())
        val responseEntity = ResponseEntity(biddings, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }
}
