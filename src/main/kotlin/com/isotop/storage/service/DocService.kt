package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.DocTypeRequest
import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.dto.response.ListDocTypeDataResponse
import com.isotop.storage.repository.DocRepository
import org.springframework.stereotype.Service

@Service
open class DocService(
    private val docRepository: DocRepository
) {
    open fun getDocTypes(): ListDocTypeDataResponse {
        return ListDocTypeDataResponse(docRepository.getDocTypes())
    }

    open fun getDocTypeById(makeId: Int): DocTypeResponse {
        if (!docRepository.isExistDocTypeById(makeId)) {
            throw ValidationException(10)
        }
        return docRepository.getDocTypeById(makeId)[0]
    }

    open fun addDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {
        validatAddDocTypeRequest(docTypeRequest)
        return docRepository.addDocType(docTypeRequest)
    }

    open fun updateDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {
        validateUpdateDocTypeRequest(docTypeRequest)
        return docRepository.updateDocType(docTypeRequest)
    }

    open fun removeDocType(id: Int) {
        if (!docRepository.isExistDocTypeById(id)) {
            throw ValidationException(21)
        }
        return docRepository.removeDocType(id)
    }

    private fun validatAddDocTypeRequest(docTypeRequest: DocTypeRequest) {
        if (docTypeRequest.docTypeName.isNullOrBlank()
            || docRepository.isExistDocTypeByName(docTypeRequest.docTypeName)
        ) {
            throw ValidationException(22)
        }
    }

    private fun validateUpdateDocTypeRequest(docTypeRequest: DocTypeRequest) {
        if (!docRepository.isExistDocTypeById(docTypeRequest.docTypeCode)) {
            throw ValidationException(23)
        }
        if (docTypeRequest.docTypeName.isNullOrBlank()
        ) {
            throw ValidationException(24)
        }
    }
}