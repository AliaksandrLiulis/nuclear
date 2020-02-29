package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListStatementListResponse
import com.isotop.storage.service.StatementListService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}