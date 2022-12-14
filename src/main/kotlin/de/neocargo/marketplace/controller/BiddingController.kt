package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.Bid
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.repository.UserRepository
import de.neocargo.marketplace.service.BiddingService
import de.neocargo.marketplace.service.UserService
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
    @Autowired
    private val userService: UserService,
    @Autowired
    private val biddingService: BiddingService,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val responseHeaders: HttpHeaders,
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

        if (responseEntity.statusCodeValue == HttpStatus.CREATED.value()) {
            userService.addBiddingToPublishedBiddings(user.id!!, bidding.id)
            val user = userRepository.findWhitelistByUserId(user.id!!)
            userService.addBiddingToAssignedBiddings(user.whitelist, bidding.id)
        }

        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/")
    @PreAuthorize("#user.id != null")
    fun findAllBiddingsByUserId(@AuthenticationPrincipal user: User): ResponseEntity<MutableSet<Bidding>> {
        val biddings = biddingService.findAllBiddings(user.id.toString())
        val responseEntity = ResponseEntity(biddings, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/assigned")
    @PreAuthorize("#user.id != null")
    fun getAssignedBiddings(@AuthenticationPrincipal user: User) : ResponseEntity<MutableSet<Bidding>> {
        val assignedBiddings = biddingService.getAssignedBiddings(user.id.toString())
        val responseEntity = ResponseEntity(assignedBiddings, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @PostMapping("/bid")
    @PreAuthorize("#user.id != null")
    fun addBidToBidding(@AuthenticationPrincipal user: User, @RequestBody bid: Bid) : ResponseEntity<MutableSet<Bidding>> {
        val bidding = biddingService.addBidToBidding(user.id.toString(), bid)
        val responseEntity = ResponseEntity(bidding, responseHeaders, HttpStatus.CREATED)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity

    }

    @PostMapping("/assignBidding")
    @PreAuthorize("#user.id != null")
    fun endBidding(@AuthenticationPrincipal user: User, @RequestBody bid: Bid) : ResponseEntity<Collection<Bidding>> {
        val bidding = biddingService.endBidding(user.id.toString(), bid.biddingId)
        val responseEntity = ResponseEntity(bidding, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }
}