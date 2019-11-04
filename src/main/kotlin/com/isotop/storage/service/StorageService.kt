package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.UpdateStorageRequest
import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.dto.response.StorageResponse
import com.isotop.storage.repository.MoutionRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StorageService(
    private val storageRepository: StorageRepository,
    private val moutionRepository: MoutionRepository

) {

    open fun getAllStorage(): ListStorageDataResponse {
        return ListStorageDataResponse(storageRepository.getAllFromStorage())
    }

    @Transactional
    open fun updateStorage(payload: UpdateStorageRequest): StorageResponse {
        if (!moutionRepository.isExistMotionByStorageId(payload.storageCode) ||
            !storageRepository.isExistStorageNoteById(payload.storageCode)
        ) {
            throw ValidationException(25)
        }
        moutionRepository.updateMoution(
            payload.makeDate,
            payload.orgCode,
            payload.storageCode,
            payload.docDate,
            payload.docNumber,
            payload.docTypeCode,
            payload.actCode
        )
        storageRepository.updateStorageNote(payload)
        return storageRepository.getStorageNoteByIdStorage(payload.storageCode)[0]
    }
}
