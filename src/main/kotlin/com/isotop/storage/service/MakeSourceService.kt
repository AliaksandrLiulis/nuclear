package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.MakeSourceRequest
import com.isotop.storage.dto.response.ListMakeSourceDataResponse
import com.isotop.storage.dto.response.MakeSourceResponse
import com.isotop.storage.repository.MakeSourcesRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class MakeSourceService(
    private val makeSourcesRepository: MakeSourcesRepository,
    private val storageRepository: StorageRepository
) {

    open fun getMakeSources(): ListMakeSourceDataResponse {
        return ListMakeSourceDataResponse(makeSourcesRepository.getAllMakeSources())
    }

    @Transactional
    open fun addMakeSource(payload: MakeSourceRequest): MakeSourceResponse {
        return makeSourcesRepository.addMakeSource(payload)
    }

    @Transactional
    open fun updateMakeSource(payload: MakeSourceRequest): MakeSourceResponse {
        if (!storageRepository.isExistStorageNoteById(payload.storageCode!!)) {
            throw ValidationException(35)
        }
        return makeSourcesRepository.updateMakeSource(payload)
    }

    @Transactional
    open fun removeMakeSource(storageCode: Int) {
        if (!storageRepository.isExistStorageNoteById(storageCode)) {
            throw ValidationException(35)
        }
        storageRepository.removeStorageNote(storageCode)
    }
}