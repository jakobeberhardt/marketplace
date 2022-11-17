package de.neocargo.marketplace.controller

import com.fasterxml.jackson.databind.ObjectMapper
import de.neocargo.marketplace.entity.Bidding
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.entity.Visit
import de.neocargo.marketplace.repository.BiddingRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@ExtendWith(MockitoExtension::class)
class BiddingControllerTest {
    @Mock
    private val biddingRepository: BiddingRepository? = null

    @InjectMocks
    private val biddingController: BiddingController? = null

    @Autowired
    private var mvc: MockMvc = MockMvcBuilders
        .standaloneSetup(biddingController)
        .build()

    // This object will be magically initialized by the initFields method below.
    private val jsonBidding: JacksonTester<Bidding>? = null
    private val jsonShipment: JacksonTester<Shipment>? = null
    private val jsonUser: JacksonTester<User>? = null
    @BeforeEach
    fun setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, ObjectMapper())
        System.out.println(jsonShipment.toString())
        // MockMvc standalone approach
        mvc = MockMvcBuilders
            .standaloneSetup(biddingController)
            .build()
    }

    private fun saveOneBidding() = biddingRepository?.save(Bidding("2", "1", prepareBiddingRequestBody()))
    private fun createOneBidding() = Bidding("2", "1", prepareBiddingRequestBody())
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

/*    @Test
    @Throws(Exception::class)
    fun canRetrieveByIdWhenExists() {
        // given
        val bidding = createOneBidding()
        given(biddingRepository?.findAllBiddingsByUserId("2"))
            .willReturn(bidding)

        // when
        val response: MockHttpServletResponse? = mvc?.perform(
            get("/api/v1/biddings/")
                .accept(MediaType.APPLICATION_JSON)
        )?.andReturn()?.getResponse()

        // then
        assertThat(response?.getStatus()).isEqualTo(HttpStatus.OK.value())
        assertThat(response?.getContentAsString()).isEqualTo(
            jsonBidding?.write(bidding)!!.getJson()
        )
    }*/

    @Test
    @Throws(Exception::class)
    fun canCreateANewBidding() {
        // when
        val shipment = prepareBiddingRequestBody()
        val user = User()
        user.setId("1")
        val response: MockHttpServletResponse = mvc.perform(post("/api/v1/biddings/")
            .with(user(user))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonShipment!!.write(shipment).getJson())
        ).andReturn().getResponse()

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value())
    }

    /*@Test
    @Throws(Exception::class)
    fun canRetrieveByIdWhenDoesNotExist() {
        // given
        given(biddingRepository.getSuperHero(2))
            .willThrow(NonExistingHeroException())

        // when
        val response: MockHttpServletResponse = mvc.perform(
            get("/superheroes/2")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andReturn().getResponse()

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(response.getContentAsString()).isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun canRetrieveByNameWhenExists() {
        // given
        given(biddingRepository.getSuperHero("RobotMan"))
            .willReturn(Optional.of(SuperHero("Rob", "Mannon", "RobotMan")))

        // when
        val response: MockHttpServletResponse = mvc.perform(
            get("/superheroes/?name=RobotMan")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andReturn().getResponse()

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.getContentAsString()).isEqualTo(
            jsonSuperHero.write(SuperHero("Rob", "Mannon", "RobotMan")).getJson()
        )
    }

    @Test
    @Throws(Exception::class)
    fun canRetrieveByNameWhenDoesNotExist() {
        // given
        given(biddingRepository.getSuperHero("RobotMan"))
            .willReturn(Optional.empty())

        // when
        val response: MockHttpServletResponse = mvc.perform(
            get("/superheroes/?name=RobotMan")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andReturn().getResponse()

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.getContentAsString()).isEqualTo("null")
    }

    @Test
    @Throws(Exception::class)
    fun headerIsPresent() {
        // when
        val response: MockHttpServletResponse = mvc.perform(
            get("/superheroes/2")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andReturn().getResponse()

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.getHeaders("X-SUPERHERO-APP")).containsOnly("super-header")
    }*/
}