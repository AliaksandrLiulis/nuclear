package com.isotop.storage.service

import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service

@Service
open class CloseSourceService(
    private val storageRepository: StorageRepository
) {

    open fun getAllCloseSource(): ListStorageDataResponse {
        return ListStorageDataResponse(storageRepository.getAllFromStorageWhereComeDateIsNull())
    }
}