package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.AddContainerToStorageRequest
import com.isotop.storage.dto.request.TransferRequest
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
        return ListStorageDataResponse(storageRepository.getAllFromStorageWhereComeDateIsNotNull())
    }

    @Transactional
    open fun addContainerToStorage(payload: AddContainerToStorageRequest): StorageResponse {
        val storageNote = storageRepository.addContainerToStorage(payload)
        if (storageNote != null) {
            moutionRepository.addMoution(
                payload.moutionType!!,
                payload.makeDate,
                payload.orgCode,
                storageNote,
                payload.docDate,
                payload.docNumber,
                payload.docTypeCode,
                payload.actCode
            )
            return storageRepository.getStorageNoteByIdStorage(storageNote)[0]
        } else {
            throw ValidationException(26)
        }
    }

    @Transactional
    open fun addSourceToStorage(payload: AddContainerToStorageRequest): StorageResponse {
        val storageNote = storageRepository.addContainerToStorage(payload)
        if (storageNote != null) {
            moutionRepository.addMoution(
                payload.moutionType!!,
                payload.comeDate,
                payload.orgCode,
                storageNote,
                payload.docDate,
                payload.docNumber,
                payload.docTypeCode,
                payload.actCode
            )
            return storageRepository.getStorageNoteByIdStorage(storageNote)[0]
        } else {
            throw ValidationException(26)
        }
    }

    @Transactional
    open fun updateStorage(payload: UpdateStorageRequest): StorageResponse {
        if (!moutionRepository.isExistMotionByStorageId(payload.storageCode) ||
            !storageRepository.isExistStorageNoteById(payload.storageCode)
        ) {
            throw ValidationException(25)
        }
        moutionRepository.updateMoutionByStorageCode(
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

    @Transactional
    open fun removeStorageNote(storageCode: Int) {
        if (!storageRepository.isExistStorageNoteById(storageCode)) {
            throw ValidationException(31)
        }
        if (moutionRepository.getCountByStorageId(storageCode) > 1) {
            throw ValidationException(30)
        }
        moutionRepository.removeMoutionsByStorageCode(storageCode)
        storageRepository.removeStorageNote(storageCode)
    }

    @Transactional
    open fun goToStorage(storageCode: Int) {
        if (!storageRepository.isExistStorageNoteById(storageCode)) {
            throw ValidationException(31)
        }
        storageRepository.updateStorageNoteWhenGoToStorage(storageCode)
        moutionRepository.insertInMoutionNoteWhenGoToStorage(storageCode)
    }

    @Transactional
    open fun goToTransfer(payload: TransferRequest) {
        if (!storageRepository.isExistStorageNoteById(payload.storageCode)) {
            throw ValidationException(31)
        }
        storageRepository.updateStorageNoteToTransfer(payload.storageCode, payload.transferDate)
        moutionRepository.insertInMoutionNoteWhenTransfer(payload)
    }
}
