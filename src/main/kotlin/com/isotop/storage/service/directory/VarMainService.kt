package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.ListVarMainResponse
import com.isotop.storage.dto.response.VarMainResponse
import com.isotop.storage.repository.directory.VarMainRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class VarMainService(
    private val varMainRepository: VarMainRepository
) {

    open fun getVarMain(): ListVarMainResponse {
        return ListVarMainResponse(varMainRepository.getVarMain())
    }

    open fun getVarMainById(varMainId: Int): VarMainResponse {
        if (!varMainRepository.isExistVarMainById(varMainId)) {
            throw ValidationException(41)
        }
        return varMainRepository.getVarMainById(varMainId)[0]
    }

    @Transactional
    open fun addVarMain(varMainRequest: VarMainRequest): VarMainResponse {
        validateVarMainRequest(varMainRequest)
        return varMainRepository.addVarMain(varMainRequest)
    }

    @Transactional
    open fun updateVarMain(varMainRequest: VarMainRequest): VarMainResponse  {
        validateUpdateVarMainRequest(varMainRequest)
        return varMainRepository.updateVarMain(varMainRequest)
    }

    @Transactional
    open fun removeVarMain(id: Int) {
        if (!varMainRepository.isExistVarMainById(id)) {
            throw ValidationException(42)
        }
        return varMainRepository.removeVarMain(id)
    }

    private fun validateVarMainRequest(varMainRequest: VarMainRequest) {
        if (varMainRequest.varName.isNullOrBlank() || varMainRequest.varValue.isNullOrBlank()
            || varMainRepository.isExistVarMainByVarName(varMainRequest.varName)
        ) {
            throw ValidationException(43)
        }
    }

    private fun validateUpdateVarMainRequest(varMainRequest: VarMainRequest) {
        if (!varMainRepository.isExistVarMainById(varMainRequest.varCode)) {
            throw ValidationException(44)
        }
        if (varMainRequest.varName.isNullOrBlank() || varMainRequest.varValue.isNullOrBlank()
        ) {
            throw ValidationException(45)
        }
    }
}
