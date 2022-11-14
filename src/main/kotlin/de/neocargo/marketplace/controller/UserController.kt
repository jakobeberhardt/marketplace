package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import de.neocargo.marketplace.security.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("${Router.API_PATH}/users")
class UserController(
    @Autowired
    val userRepository: UserRepository) {

    @GetMapping("/{id}")
    @PreAuthorize("#user.id == #id")
    fun user(@AuthenticationPrincipal user: User, @PathVariable id: String): ResponseEntity<UserDTO> {
        val responseHeaders = HttpHeaders()
        responseHeaders[HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN] = "*"
        return ResponseEntity(UserDTO.from(userRepository.findById(id)), responseHeaders, HttpStatus.OK)
    }
}