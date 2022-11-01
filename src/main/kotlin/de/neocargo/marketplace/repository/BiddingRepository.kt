package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Bidding
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ContractRepository : MongoRepository<Bidding, Long>{
    @Query("{ 'id' : ?0 }")
    fun findByContractId(id: String): Bidding?

    @Query("{ 'userId' : ?0 }")
    fun findAllByUserId(id: Long): List<Bidding>?
}