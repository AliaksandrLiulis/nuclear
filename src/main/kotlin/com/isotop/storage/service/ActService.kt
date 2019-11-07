package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ActRequest
import com.isotop.storage.dto.response.ActResponse
import com.isotop.storage.dto.response.ListActsDataResponse
import com.isotop.storage.repository.ActRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ActService(
    private val actRepository: ActRepository
) {
    open fun getActs(): ListActsDataResponse {
        return ListActsDataResponse(actRepository.getActs())
    }

    open fun getActById(actId: Int): ActResponse {
        if (!actRepository.isExistActById(actId)) {
            throw ValidationException(27)
        }
        return actRepository.getActById(actId)[0]
    }

    @Transactional
    open fun addAct(actRequest: ActRequest): ActResponse {
        validatAddActRequest(actRequest)
        return actRepository.addAct(actRequest)
    }

    @Transactional
    open fun updateAct(actRequest: ActRequest): ActResponse {
        validateUpdateActRequest(actRequest)
        return actRepository.updateAct(actRequest)
    }

    @Transactional
    open fun removeActById(actId: Int) {
        if (!actRepository.isExistActById(actId)) {
            throw ValidationException(28)
        }
        return actRepository.removeActById(actId)
    }

    private fun validatAddActRequest(actRequest: ActRequest) {
        if (actRequest.actNote.isNullOrBlank()
            || actRequest.actFormatCode < 0
            || actRequest.actNumber.isNullOrBlank()
        ) {
            throw ValidationException(29)
        }
    }

    private fun validateUpdateActRequest(actRequest: ActRequest) {
        if (!actRepository.isExistActById(actRequest.actCode)) {
            throw ValidationException(28)
        }
        validatAddActRequest(actRequest)
    }
}
