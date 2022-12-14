package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Bidding
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface BiddingRepository : MongoRepository<Bidding, Long> {
    @Query("{ 'id' : ?0 }")
    fun findBiddingById(id: String): Bidding?

    @Query("{ 'userId' : ?0 }")
    fun findAllBiddingsByUserId(userId: String): MutableSet<Bidding>?
}

