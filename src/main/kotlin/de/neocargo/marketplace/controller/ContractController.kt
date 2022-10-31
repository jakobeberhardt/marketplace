package de.neocargo.marketplace.controller

import de.neocargo.marketplace.entity.Contract
import de.neocargo.marketplace.entity.Shipment
import de.neocargo.marketplace.repository.ContractRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
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

    @GetMapping("/{id}")
    fun findContractById(@PathVariable("id")id : String): ResponseEntity<Contract> {
        val contract = contractRepository.findByContractId(id)
        return ResponseEntity.ok(contract)
    }

    @GetMapping("/user/{userId}")
    fun findAllContractsByUserId(@PathVariable("userId")userId : Long): ResponseEntity<List<Contract>> {
        val contracts = contractRepository.findAllByUserId(userId)
        return ResponseEntity.ok(contracts)
    }
}