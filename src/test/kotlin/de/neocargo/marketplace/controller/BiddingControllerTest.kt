package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.repository.BiddingRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BiddingControllerTest (
    @Autowired
    private val biddingRepository: BiddingRepository,
    @Autowired
    private val restTemplate: TestRestTemplate,
) {
    private val defaultBiddingId: String = "00000000-0000-0000-0000-000000000001"
    private val defaultUserId: Long = 1
    private val defaultShipmentId: Long = 1

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        biddingRepository.deleteAll()
    }

    @Test
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

    private fun getRootUrl(): String? = "http://localhost:$port/biddings"

    private fun saveOneTask() = biddingRepository.save(Bidding(defaultBiddingId, 1, prepareBiddingRequestBody()))

    private fun prepareBiddingRequestBody(): Shipment = Shipment(
        1,
        "Test",
        1,
        "Test",
        null,
        "Test",
        null,
        "Test",
        1,
        1.1,
        1.1,
        null,
        "Test",
        null,
    )
}