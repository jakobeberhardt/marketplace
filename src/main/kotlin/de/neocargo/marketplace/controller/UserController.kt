package de.neocargo.marketplace.controller

import de.neocargo.marketplace.config.Router
import de.neocargo.marketplace.entity.User
import de.neocargo.marketplace.repository.UserRepository
import de.neocargo.marketplace.security.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
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
    fun user(@AuthenticationPrincipal user: User, @PathVariable id: String): ResponseEntity<*> {
        return ResponseEntity.ok(UserDTO.from(userRepository.findById(id)))
    }
}