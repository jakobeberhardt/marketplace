package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.Bid
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Status
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.security.dto.BidDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BiddingService(
    @Autowired
    private val biddingRepository: BiddingRepository,
    @Autowired
    private val userService: UserService,
) {

    fun getAssignedBiddings(userId : String) : MutableSet<Bidding>{
        val user : User = userService.findById(userId)
        val assignedBiddings : MutableSet<Bidding> = mutableSetOf()
        for (i in user.assignedBiddings) {
            var bidding = biddingRepository.findBiddingById(i)!!
            bidding.bids = bidding.bids.filter { it.userId == userId }.toMutableSet()
            assignedBiddings.add(bidding)
        }
        return assignedBiddings
}
    fun findAllBiddings(userId: String): MutableSet<Bidding>? {
        return biddingRepository.findAllBiddingsByUserId(userId)

    }

    fun findAllActiveBiddings(userId: String): MutableSet<Bidding>? {
        return biddingRepository.findAllBiddingsByUserId(userId)?.filter { it.status.equals(Status.ACTIVE) }?.toMutableSet()
    }

    fun addBidToBidding(userId: String, bidDto: BidDTO): Bidding? {
        val user : User = userService.findById(userId)
        val bid = Bid(userId = userId, biddingId = bidDto.biddingId, value = bidDto.value, currency = bidDto.currency)
        user.bids.add(bid)
        userService.saveUser(user)
        val bidding = biddingRepository.findBiddingById(bid.biddingId)
        bidding!!.bids.add(bid)
        biddingRepository.save(bidding)

        return bidding

    }



    fun endBidding(userId: String, biddingId: String): Bidding? {
        val bidding = biddingRepository.findBiddingById(biddingId)
        if (bidding != null) {
                bidding.status = Status.FINISHED
                biddingRepository.save(bidding)
        }
        return bidding
    }
}

