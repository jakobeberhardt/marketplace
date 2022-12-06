package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.Bid
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BiddingService(
    @Autowired
    private val biddingRepository: BiddingRepository,
    @Autowired
    private val userRepository: UserRepository,
) {

    fun getAssignedBiddings(userId : String) : MutableSet<Bidding>{
        val user : User = userRepository.findById(userId)
        val assignedBiddings : MutableSet<Bidding> = mutableSetOf()
        for (i in user.assignedBiddings)
            assignedBiddings.add(biddingRepository.findBiddingById(i)!!)
        return assignedBiddings
}
    fun findAllBiddings(userId: String): MutableSet<Bidding> {
        val biddings = biddingRepository.findAllBiddingsByUserId(userId)
        if (biddings != null) {
            return biddings
        }
        return mutableSetOf()
    }

    fun addBidToBidding(userId: String, bid : Bid): Bidding {
        val user : User = userRepository.findById(userId)
        val bid = Bid(userId = userId,
            biddingId = bid.biddingId,
            value = bid.value,
            currency = bid.currency)
        user.bids.add(bid)
        val bidding = biddingRepository.findBiddingById(bid.biddingId)
        bidding!!.bids.add(bid)
        return bidding
    }

}