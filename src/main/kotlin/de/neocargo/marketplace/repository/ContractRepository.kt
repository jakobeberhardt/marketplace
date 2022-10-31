package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Contract
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ContractRepository : MongoRepository<Contract, Long>{
    @Query("{ 'id' : ?0 }")
    fun findByContractId(id: String): Contract?

    @Query("{ 'userId' : ?0 }")
    fun findAllByUserId(id: Long): List<Contract>?
}