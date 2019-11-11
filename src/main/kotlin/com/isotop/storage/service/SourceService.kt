package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.AddContainerRequest
import com.isotop.storage.dto.response.StorageResponse
import com.isotop.storage.repository.MoutionRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SourceService(
    private val moutionRepository: MoutionRepository,
    private val storageRepository: StorageRepository
) {
    @Transactional
    open fun addSource(payload: AddContainerRequest): StorageResponse {
        val storageNote = storageRepository.addStorageNote(payload)
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
}
