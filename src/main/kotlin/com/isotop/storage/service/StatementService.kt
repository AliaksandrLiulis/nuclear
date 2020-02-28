package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.ListStatementResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.repository.StatementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StatementService(
    private val statementRepository: StatementRepository
) {

    open fun getStatement(): ListStatementResponse {
        return ListStatementResponse(statementRepository.getStatement())
    }

    open fun getStatementById(statementId: Int): StatementResponse {
        if (!statementRepository.isExistStatementById(statementId)) {
            throw ValidationException(46)
        }
        return statementRepository.getStatementById(statementId)
    }

    @Transactional
    open fun addStatement(varMainRequest: VarMainRequest): StatementResponse {
        validateVarMainRequest(varMainRequest)
        return statementRepository.addStatement(varMainRequest)
    }

    @Transactional
    open fun updateStatement(varMainRequest: VarMainRequest): StatementResponse {
        validateUpdateVarMainRequest(varMainRequest)
        return statementRepository.updateStatement(varMainRequest)
    }

    @Transactional
    open fun removeStatement(id: Int) {
        if (!statementRepository.isExistStatementById(id)) {
            throw ValidationException(46)
        }
        return statementRepository.removeStatement(id)
    }

    private fun validateVarMainRequest(varMainRequest: VarMainRequest) {
        if (varMainRequest.varName.isNullOrBlank() || varMainRequest.varValue.isNullOrBlank()
//            || varMainRepository.isExistVarMainByVarName(varMainRequest.varName)
        ) {
            throw ValidationException(43)
        }
    }

    private fun validateUpdateVarMainRequest(varMainRequest: VarMainRequest) {
//        if (!varMainRepository.isExistVarMainById(varMainRequest.varCode)) {
//            throw ValidationException(44)
//        }
        if (varMainRequest.varName.isNullOrBlank() || varMainRequest.varValue.isNullOrBlank()
        ) {
            throw ValidationException(45)
        }
    }
}
