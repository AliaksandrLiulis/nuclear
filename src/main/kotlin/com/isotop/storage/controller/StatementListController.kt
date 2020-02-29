package com.isotop.storage.controller

import com.isotop.storage.dto.request.StatementListRequest
import com.isotop.storage.dto.response.ListStatementListResponse
import com.isotop.storage.dto.response.StatementListResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.service.StatementListService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/statementlists")
open class StatementListController(
    private val statementListService: StatementListService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{statementId}", produces = ["application/json"])
    open fun getStatementList(
        @PathVariable(value = "statementId")
        statementId: Int
    ): ListStatementListResponse {
        return statementListService.getStatementList(statementId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addStatementListNote(
        @RequestBody
        payload: StatementListRequest
    ): StatementListResponse {
        return statementListService.addStatementListNote(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{statementListCode}")
    open fun deleteStatementListNote(
        @PathVariable(value = "statementListCode")
        statementListCode: Int
    ) {
        return statementListService.removeStatementNote(statementListCode)
    }
}