package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.Bid
import de.neocargo.marketplace.entity.Bidding
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
        for (i in user.assignedBiddings)
            assignedBiddings.add(biddingRepository.findBiddingById(i)!!)
        return assignedBiddings
}
    fun findAllBiddings(userId: String): MutableSet<Bidding> {
        val biddings = biddingRepository.findAllBiddingsByUserId(userId)
        if (!biddings.isNullOrEmpty()) {
            return biddings
        }
        return mutableSetOf()
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



    fun endBidding(userId: String, biddingId: String): Collection<Bidding> {
        val bidding = biddingRepository.findAllBiddingsByUserId(userId)
        if (bidding != null) {
            for (i in bidding)
                i.active = false
            return bidding.filter { it.active }
        }
        return mutableSetOf()
    }

}

