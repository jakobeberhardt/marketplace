/*
package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.entity.Visit
import de.neocargo.marketplace.repository.BiddingRepository
import de.neocargo.marketplace.security.TokenGenerator
import de.neocargo.marketplace.service.UserManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Alt (
    @Autowired
    private val biddingRepository: BiddingRepository,
    @Autowired
    private val userManager: UserManager,
    @Autowired
    private val daoAuthenticationProvider: DaoAuthenticationProvider,
    @Autowired
    private val tokenGenerator: TokenGenerator,
    @Autowired
    private val restTemplate: TestRestTemplate,
) {
    private var accessToken: String? = ""



    private val defaultBiddingId: String = "00000000-0000-0000-0000-000000000001"
    private val defaultUserId: Long = 1
    private val defaultShipmentId: Long = 1

    private fun getRootUrl(): String? = "http://localhost:$port/api/v1/biddings"
    private fun saveOneBidding() = biddingRepository.save(Bidding(defaultBiddingId, "1", prepareBiddingRequestBody()))
    private fun prepareBiddingRequestBody(): Shipment = Shipment(
        1,
        "Test",
        1,
        "Test",
        Visit(
            null,
            "test",
            1,
            null,
            null,
            "Test"
        ),
        "Test",
        Visit(
            null,
            "test",
            1,
            null,
            null,
            "Test"
        ),
        "Test",
        1,
        1.1,
        1.1,
        1.1,
        "Test",
        null,
    )

    private fun saveOneUser() {
        val user = User()
        user.setUsername("TestUser")
        user.setPassword("1234")
        userManager.createUser(user)
    }

    private fun updateAccessToken() {
        val authentication = daoAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(
                "TestUser",
                "1234"
            )
        )
        accessToken = tokenGenerator.createToken(authentication).accessToken
    }

    @LocalServerPort
    protected var port: Int = 0

    @BeforeAll
    fun setUpUser(){
        saveOneUser()
        updateAccessToken()
    }

    */
/*    @AfterAll
        fun deleteUser() {
            userManager.deleteUser("TestUser")
        }*//*


    @BeforeEach
    fun cleanUpBiddingRepository() {
        biddingRepository.deleteAll()
    }

    @Test
    @WithMockUser
    fun createBidding() {
        saveOneTask()
        val biddingRequestBody = prepareBiddingRequestBody()

        val response = restTemplate.postForEntity(
            getRootUrl(),
            biddingRequestBody,
            Bidding::class.java
        )

        assertEquals(201, response.statusCode.value())
        assertNotNull(response.body)
        assertNotNull(response.body?.id)
        assertEquals(defaultBiddingId, response.body?.id)
        assertEquals(defaultUserId, response.body?.userId)
        assertEquals(biddingRequestBody.id, response.body?.shipment?.id)
    }

    @Test
    fun getAllBiddings() {
    }

    @Test
    fun findBiddingById() {
    }

    @Test
    fun findAllBiddingsByUserId() {
    }
}*/
