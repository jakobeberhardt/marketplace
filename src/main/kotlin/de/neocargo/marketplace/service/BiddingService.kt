package de.neocargo.marketplace.service

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
    private val userRepository: UserRepository
) {

    fun getAssignedBiddings(userId : String) : MutableSet<Bidding>{
    val user : User = userRepository.findById(userId)
    val assignedBiddings : MutableSet<Bidding> = mutableSetOf()
    for (i in user.assignedBiddings)
    assignedBiddings.add(biddingRepository.findByBiddingId(i)!!)
        return assignedBiddings
}}