package de.neocargo.marketplace.repository

import de.neocargo.marketplace.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

    interface UserRepository : MongoRepository<de.neocargo.marketplace.entity.User, Long> {
        @Query("{ 'id' : ?0 }")
        fun findById(id: String): de.neocargo.marketplace.entity.User

        @Query("{ 'id' : ?0 }")
        fun existsById(id: String): Boolean

        @Query("{ 'username' : ?0 }")
        fun findByUsername(username: String): de.neocargo.marketplace.entity.User

        @Query(value = "{_id : ?0}", fields = "{whitelist: 1}")
        fun findWhitelistByUserId(id: String): User
    }