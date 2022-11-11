package de.neocargo.marketplace.repository

import org.springframework.security.core.userdetails.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserRepository : MongoRepository<de.neocargo.marketplace.entity.User, Long>{
    @Query("{ 'id' : ?0 }")
    fun findById(id: String): User

    @Query("{ 'id' : ?0 }")
    fun existsById(id: String): Boolean

    @Query("{ 'username' : ?0 }")
    fun findByUsername(username: String): User
}