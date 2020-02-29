package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.StatementAddRequest
import com.isotop.storage.dto.request.StatementUpdateRequest
import com.isotop.storage.dto.response.ListSourceFromStorageResponse
import com.isotop.storage.dto.response.ListStatementResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.repository.StatementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StatementService(
    private val statementRepository: StatementRepository
) {

    open fun getStatement(statementType: Int): ListStatementResponse {
        return ListStatementResponse(statementRepository.getStatement(statementType))
    }

    open fun getStatementById(statementId: Int): StatementResponse {
        if (!statementRepository.isExistStatementById(statementId)) {
            throw ValidationException(46)
        }
        return statementRepository.getStatementById(statementId)
    }

    open fun getAllSourceFromStorageOnRegister(): ListSourceFromStorageResponse {
        return ListSourceFromStorageResponse(statementRepository.getAllSourceFromStorageOnRegister())
    }

    @Transactional
    open fun addStatement(statementRequest: StatementAddRequest): StatementResponse {
        return statementRepository.addStatement(statementRequest)
    }

    @Transactional
    open fun updateStatement(updateRequest: StatementUpdateRequest): StatementResponse {
        validateUpdateVarMainRequest(updateRequest)
        return statementRepository.updateStatement(updateRequest)
    }

    @Transactional
    open fun removeStatement(id: Int) {
        if (!statementRepository.isExistStatementById(id)) {
            throw ValidationException(46)
        }
        return statementRepository.removeStatement(id)
    }

    private fun validateUpdateVarMainRequest(statementUpdateRequestRequest: StatementUpdateRequest) {
        if (!statementRepository.isExistStatementById(statementUpdateRequestRequest.statementCode)) {
            throw ValidationException(46)
        }
    }
}
