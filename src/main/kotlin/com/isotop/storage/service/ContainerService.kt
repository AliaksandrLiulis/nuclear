package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListContainerDataResponse
import com.isotop.storage.repository.ContainerRepository
import com.isotop.storage.repository.MoutionRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service

@Service
open class ContainerService(
    private val moutionRepository: MoutionRepository,
    private val storageRepository: StorageRepository,
    private val containerRepository: ContainerRepository

) {

    open fun getAllFromContainer(): ListContainerDataResponse {
        return ListContainerDataResponse(containerRepository.getAllFromContainer())
    }

    open fun getContainersByStorageCode(idStorageCode: Int): ListContainerDataResponse {
        if (!storageRepository.isExistStorageContainerNoteById(idStorageCode)) {
            throw ValidationException(32)
        }
        if (!containerRepository.isExistContainerByStorageCode(idStorageCode)) {
            return ListContainerDataResponse(listOf())
        }
        return ListContainerDataResponse(containerRepository.getListContainerByStorageCode(idStorageCode))
    }

}