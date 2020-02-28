package com.isotop.storage.controller

import com.isotop.storage.dto.request.StatementAddRequest
import com.isotop.storage.dto.request.StatementUpdateRequest
import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.ListStatementResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.service.StatementService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/statements")
open class StatementController(
    private val statementService: StatementService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/in",produces = ["application/json"])
    open fun getStatementsIn(): ListStatementResponse {
        return statementService.getStatement(1)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/out",produces = ["application/json"])
    open fun getStatementsOut(): ListStatementResponse {
        return statementService.getStatement(2)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{statementId}", produces = ["application/json"])
    open fun getStatementById(
        @PathVariable(value = "statementId")
        statementId: Int
    ): StatementResponse {
        return statementService.getStatementById(statementId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addStatement(
        @RequestBody
        payload: StatementAddRequest
    ): StatementResponse {
        return statementService.addStatement(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateStatement(
        @RequestBody
        payload: StatementUpdateRequest
    ): StatementResponse {
        return statementService.updateStatement(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{statementId}")
    open fun deleteStatement(
        @PathVariable(value = "statementId")
        statementId: Int
    ) {
        return statementService.removeStatement(statementId)
    }
}
