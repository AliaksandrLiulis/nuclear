package com.isotop.storage.service

import com.isotop.storage.dto.response.ListStatementListResponse
import com.isotop.storage.repository.StatementListRepository
import org.springframework.stereotype.Service

@Service
open class StatementListService(
    private val statementListRepository: StatementListRepository
) {

    open fun getStatementList(statementCode: Int): ListStatementListResponse {
        return ListStatementListResponse(statementListRepository.getStatementList(statementCode))
    }
}