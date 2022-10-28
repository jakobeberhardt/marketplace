package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.Contract
import org.springframework.data.mongodb.repository.MongoRepository

interface ContractRepository : MongoRepository<Contract, Long>