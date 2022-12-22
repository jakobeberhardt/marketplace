package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Bid
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface BidRepository : MongoRepository<Bid, Long>   {

    @Query("{ 'id' : ?0 }")
    fun findBidById(id: String): Bid
}