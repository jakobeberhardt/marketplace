package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.Bid
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.function.Predicate

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

    fun addBidToBidding(userId: String, bid : Bid): MutableSet<Bidding> {
        val user : User = userService.findById(userId)
        user.bids.add(bid)
        userService.saveUser(user)
        val bidding = biddingRepository.findBiddingById(bid.biddingId)
        bidding!!.bids.add(bid)
        biddingRepository.save(bidding)

        val biddings : MutableSet<Bidding> = findAllBiddings(userId)
        for (i in biddings)
           for (k in i.bids)
               i.bids.removeIf { k.userId != userId }
                   return biddings
    }
}

