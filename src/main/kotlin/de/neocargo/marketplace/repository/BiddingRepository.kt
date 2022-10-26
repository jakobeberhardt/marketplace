package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Bidding
import org.springframework.data.mongodb.repository.MongoRepository

interface BiddingRepository : MongoRepository<Bidding, Long>