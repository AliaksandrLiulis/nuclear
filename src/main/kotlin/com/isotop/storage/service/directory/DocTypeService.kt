package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.DocTypeRequest
import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.dto.response.ListDocTypeDataResponse
import com.isotop.storage.repository.directory.DocTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class DocTypeService(
    private val docTypeRepository: DocTypeRepository
) {
    open fun getDocType(): ListDocTypeDataResponse {
        return ListDocTypeDataResponse(docTypeRepository.getDocTypes())
    }

    open fun getDocTypeByTypeId(makeId: Int): DocTypeResponse {
        if (!docTypeRepository.isExistDocTypeById(makeId)) {
            throw ValidationException(10)
        }
        return docTypeRepository.getDocTypeById(makeId)[0]
    }

    @Transactional
    open fun addDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {
        validateAddDocTypeRequest(docTypeRequest)
        return docTypeRepository.addDocType(docTypeRequest)
    }

    @Transactional
    open fun updateDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {
        validateUpdateDocTypeRequest(docTypeRequest)
        return docTypeRepository.updateDocType(docTypeRequest)
    }

    @Transactional
    open fun removeDocType(id: Int) {
        if (!docTypeRepository.isExistDocTypeById(id)) {
            throw ValidationException(21)
        }
        return docTypeRepository.removeDocType(id)
    }

    private fun validateAddDocTypeRequest(docTypeRequest: DocTypeRequest) {
        if (docTypeRequest.docTypeName.isNullOrBlank()
            || docTypeRepository.isExistDocTypeByName(docTypeRequest.docTypeName)
        ) {
            throw ValidationException(22)
        }
    }

    private fun validateUpdateDocTypeRequest(docTypeRequest: DocTypeRequest) {
        if (!docTypeRepository.isExistDocTypeById(docTypeRequest.docTypeCode)) {
            throw ValidationException(23)
        }
        if (docTypeRequest.docTypeName.isNullOrBlank()
        ) {
            throw ValidationException(24)
        }
    }
}
