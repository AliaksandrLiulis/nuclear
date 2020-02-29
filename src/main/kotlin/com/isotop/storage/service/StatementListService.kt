package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListStatementListResponse
import com.isotop.storage.repository.StatementListRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StatementListService(
    private val statementListRepository: StatementListRepository
) {

    open fun getStatementList(statementCode: Int): ListStatementListResponse {
        return ListStatementListResponse(statementListRepository.getStatementList(statementCode))
    }

    @Transactional
    open fun removeStatementNote(statementListCode: Int) {
        if (!statementListRepository.isExistStatementNoteByListCode(statementListCode)) {
            throw ValidationException(47)
        }
        return statementListRepository.removeStatementNoteList(statementListCode)
    }
}
