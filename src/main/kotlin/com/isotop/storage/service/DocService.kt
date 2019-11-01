package com.isotop.storage.service

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
}