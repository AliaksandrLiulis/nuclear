package com.isotop.storage.service

import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service

@Service
open class StorageService(
    private val storageRepository: StorageRepository
) {

    open fun getAllStorage(): ListStorageDataResponse {
        return ListStorageDataResponse(storageRepository.getAllFromStorage())
    }
}
