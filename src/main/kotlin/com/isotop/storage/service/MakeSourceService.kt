package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.MakeSourceRequest
import com.isotop.storage.dto.response.ListMakeSourceDataResponse
import com.isotop.storage.dto.response.MakeSourceResponse
import com.isotop.storage.repository.MakeSourcesRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service

@Service
open class MakeSourceService(
    private val makeSourcesRepository: MakeSourcesRepository,
    private val storageRepository: StorageRepository
) {

    fun getMakeSources(): ListMakeSourceDataResponse {
        return ListMakeSourceDataResponse(makeSourcesRepository.getAllMakeSources())
    }

    fun addMakeSource(payload: MakeSourceRequest): MakeSourceResponse {
        return makeSourcesRepository.addMakeSource(payload)
    }

    fun updateMakeSource(payload: MakeSourceRequest): MakeSourceResponse {
        if (!storageRepository.isExistStorageNoteById(payload.storageCode!!)) {
            throw ValidationException(35)
        }
        return makeSourcesRepository.updateMakeSource(payload)
    }


}