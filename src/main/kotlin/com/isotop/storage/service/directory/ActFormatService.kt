package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ActFormatAddRequest
import com.isotop.storage.dto.request.ActFormatUpdateRequest
import com.isotop.storage.dto.response.ActFormatResponse
import com.isotop.storage.dto.response.ListActsFormatDataResponse
import com.isotop.storage.repository.directory.ActFormatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ActFormatService(
    private val actFormatRepository: ActFormatRepository
) {

    open fun getActFormats(): ListActsFormatDataResponse {
        return ListActsFormatDataResponse(actFormatRepository.getActFormats())
    }

    @Transactional
    open fun addActFormat(request: ActFormatAddRequest): ActFormatResponse {
        validatAddActFormatRequest(request.actFormatName)
        return actFormatRepository.addActFormat(request.actFormatName)
    }

    @Transactional
    open fun updateDocType(request: ActFormatUpdateRequest): ActFormatResponse {
        validateUpdateActFormatRequest(request)
        return actFormatRepository.updateActFormat(request)
    }

    @Transactional
    open fun removeDocType(id: Int) {
        if (!actFormatRepository.isExistActFormatById(id)) {
            throw ValidationException(39)
        }
        return actFormatRepository.removeActFormat(id)
    }

    private fun validatAddActFormatRequest(request: String) {
        if (request.isNullOrBlank()
        ) {
            throw ValidationException(38)
        }
    }

    private fun validateUpdateActFormatRequest(request: ActFormatUpdateRequest) {
        if (!actFormatRepository.isExistActFormatById(request.actFormatCode)) {
            throw ValidationException(39)
        }
        if (request.actFormatName.isNullOrBlank()
        ) {
            throw ValidationException(40)
        }
    }
}
