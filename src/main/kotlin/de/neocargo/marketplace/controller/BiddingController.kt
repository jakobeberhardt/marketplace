package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.*
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.repository.UserRepository
import de.neocargo.marketplace.security.dto.BidDTO
import de.neocargo.marketplace.security.dto.ChangeStatusDTO
import de.neocargo.marketplace.service.BiddingService
import de.neocargo.marketplace.service.UserService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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
    fun addBidToBidding(@AuthenticationPrincipal user: User, @RequestBody bid: BidDTO) : ResponseEntity<Bidding> {
        val bidding = biddingService.addBidToBidding(user.id.toString(), bid)
        val responseEntity = ResponseEntity(bidding, responseHeaders, HttpStatus.CREATED)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity

    }

    // TODO: No userId check in Service class. Possibly harmful code.
    @PostMapping("/assignBidding")
    @PreAuthorize("#user.id != null")
    fun endBidding(@AuthenticationPrincipal user: User, @RequestBody bid: Bid) : ResponseEntity<String> {
        val bidding = biddingService.endBidding(user.id.toString(), bid.biddingId)
        val responseEntity = ResponseEntity(bidding?.id, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @GetMapping("/{status}/")
    @PreAuthorize("#user.id != null")
    fun getActiveBiddings(@AuthenticationPrincipal user: User, @PathVariable("status") status: String) : ResponseEntity<MutableSet<Bidding>> {
        if (Status.from(status) == null) {
            return ResponseEntity(mutableSetOf(), responseHeaders, HttpStatus.NOT_ACCEPTABLE)
            }

        val biddingsOfStatus = biddingService.findAllBiddingsByStatus(user.id.toString(), Status.from(status)!!)
        val responseEntity = ResponseEntity(biddingsOfStatus, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @PutMapping("/status")
    @PreAuthorize("#user.id != null")
    fun changeStatus(@AuthenticationPrincipal user: User, @RequestBody alteredBidding: ChangeStatusDTO) : ResponseEntity<Bidding>? {
        if (Status.from(alteredBidding.newStatus) == null) {
            return ResponseEntity(null, responseHeaders, HttpStatus.NOT_ACCEPTABLE)
        }
        val bidding = biddingService.changeStatus(user.id.toString(), alteredBidding.biddingId, Status.from(alteredBidding.newStatus)!!)
        val responseEntity = ResponseEntity(bidding, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

}