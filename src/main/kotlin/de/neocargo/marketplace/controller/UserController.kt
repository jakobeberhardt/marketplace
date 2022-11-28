package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.security.dto.WhitelistDTO.Companion.from
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.security.dto.WhitelistDTO
import de.neocargo.marketplace.service.UserService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("${Router.API_PATH}/whitelist")
class UserController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val responseHeaders: HttpHeaders
) {
    @GetMapping("/")
    @PreAuthorize("#user.id != null")
    fun getWhitelist(@AuthenticationPrincipal user: User): ResponseEntity<WhitelistDTO> {
        val updatedUser: User = userService.getWhitelist(user.id.toString())
        val send = from(updatedUser)
        val responseEntity = ResponseEntity(send, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @PostMapping
    @PreAuthorize("#user.id != null")
    fun addUserToWhitelist(@AuthenticationPrincipal user: User, @RequestBody newUser: String): ResponseEntity<WhitelistDTO> {
        val updatedUser: User = userService.addUserToWhitelist(user.id.toString(), newUser)
        val send = from(updatedUser)
        val responseEntity = ResponseEntity(send, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }

    @DeleteMapping
    @PreAuthorize("#user.id != null")
    fun deleteUserFromWhitelist(@AuthenticationPrincipal user: User, @RequestBody removeUser: String): ResponseEntity<WhitelistDTO> {
        val updatedUser: User = userService.deleteUserFromWhitelist(user.id.toString(), removeUser)
        val send = from(updatedUser)
        val responseEntity = ResponseEntity(send, responseHeaders, HttpStatus.OK)
        logger.info(responseEntity.statusCode.toString())
        logger.debug(responseEntity.toString())
        return responseEntity
    }
}