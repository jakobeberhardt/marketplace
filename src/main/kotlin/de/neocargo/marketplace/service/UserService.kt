@file:Suppress("UNREACHABLE_CODE")

package de.neocargo.marketplace.service

import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val mongoTemplate: MongoTemplate,
    ) {

    fun getWhitelist(userId: String): ArrayList<String> = userRepository.findById(userId).whitelist as ArrayList<String>

    fun addUserToWhitelist(userId: String, newUserId: String) : User {
        val updatedUser: User = userRepository.findById(userId)
        if (!(updatedUser.whitelist.contains(newUserId))) {
            updatedUser.whitelist.add(newUserId)
            userRepository.save(updatedUser)
        }
        updatedUser.publishedBiddings.forEach {
            addBiddingToAssignedBiddings(listOf(newUserId), it);
        }
        return updatedUser
    }

    fun deleteUserFromWhitelist(userId: String, removeUserId: String): User {
        val updatedUser : User = userRepository.findById(userId)
        if (updatedUser.whitelist.contains(removeUserId)) {
            updatedUser.whitelist.remove(removeUserId)
            userRepository.save(updatedUser)
        }
        updatedUser.publishedBiddings.forEach {
            deleteBiddingFromAssignedBiddings(listOf(removeUserId), it);
        }
        return updatedUser
    }

    fun addBiddingToPublishedBiddings(userId: String, biddingId: String){
        val query = Query()
        query.addCriteria(Criteria.where("_id").isEqualTo(userId))
        val update = Update()
        update.addToSet("publishedBiddings", biddingId)
        mongoTemplate.updateMulti(query, update, User::class.java)
    }

    fun addBiddingToAssignedBiddings(userId: List<String>, biddingId: String){
        val query = Query()
        query.addCriteria(Criteria.where("_id").inValues(userId))
        val update = Update()
        update.addToSet("assignedBiddings", biddingId)
        mongoTemplate.updateMulti(query, update, User::class.java)
    }

    fun deleteBiddingFromAssignedBiddings(userId: List<String>, biddingId: String){
        val query = Query()
        query.addCriteria(Criteria.where("_id").inValues(userId))
        val update = Update()
        update.pull("assignedBiddings", biddingId)
        mongoTemplate.updateMulti(query, update, User::class.java)
    }

    fun findById(userId : String) : User = userRepository.findById(userId)

    fun saveUser(user: User) : User = userRepository.save(user)
}
