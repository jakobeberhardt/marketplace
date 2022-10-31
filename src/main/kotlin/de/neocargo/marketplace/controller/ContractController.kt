package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Contract
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.repository.ContractRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin()
@RequestMapping("/contracts")
class ContractController(
    private val contractRepository: ContractRepository
) {
    @PostMapping
    fun createContract(@RequestBody request: Shipment): ResponseEntity<Contract> {
        val contract = contractRepository.save(
            Contract(
                userId = request.userId,
                shipment = request.shipment
            )
        )
        return ResponseEntity(contract, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllContracts(): ResponseEntity<List<Contract>> {
        val contracts = contractRepository.findAll()
        return ResponseEntity.ok(contracts)
    }
}