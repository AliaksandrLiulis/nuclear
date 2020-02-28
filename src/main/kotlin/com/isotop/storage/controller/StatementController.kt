package com.isotop.storage.controller

import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.ListStatementResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.service.StatementService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/statements")
open class StatementController(
    private val statementService: StatementService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getStatements(): ListStatementResponse {
        return statementService.getStatement()
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
    @PostMapping
    open fun addStatement(
        @RequestBody
        payload: VarMainRequest
    ): StatementResponse {
        return statementService.addStatement(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateStatement(
        @RequestBody
        payload: VarMainRequest
    ): StatementResponse {
        return statementService.updateStatement(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteStatementById(
        @PathVariable(value = "id")
        id: Int
    ) {
        return statementService.removeStatement(id)
    }
}
