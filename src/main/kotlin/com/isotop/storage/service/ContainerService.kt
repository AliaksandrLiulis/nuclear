package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.containerRequest
import com.isotop.storage.dto.response.ContainerResponse
import com.isotop.storage.dto.response.ListContainerDataResponse
import com.isotop.storage.repository.ContainerRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ContainerService(
    private val storageRepository: StorageRepository,
    private val containerRepository: ContainerRepository

) {

    open fun getAllFromContainer(): ListContainerDataResponse {
        return ListContainerDataResponse(containerRepository.getAllFromContainer())
    }

    open fun getContainerByStorageCode(storageId: Int): ListContainerDataResponse {
        if (!containerRepository.isExistContainerByStorageCode(storageId)) {
            throw ValidationException(31)
        }
        if (!storageRepository.isExistStorageContainerNoteById(storageId)) {
            throw ValidationException(32)
        }

        return ListContainerDataResponse(containerRepository.getContainerByStorageCode(storageId))
    }

    open fun getContainerByContainerCode(containerCode: Int): ContainerResponse {
        if (!containerRepository.isExistContainerByContainerCode(containerCode)) {
            throw ValidationException(33)
        }
        return containerRepository.getContainerByContainerCode(containerCode)
    }

    @Transactional
    open fun addContainer(payload: containerRequest): ContainerResponse {
        if (!storageRepository.isExistStorageContainerNoteById(payload.storageCode)) {
            throw ValidationException(32)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val containerCode = containerRepository.addContainerAndGetCommonActivity(payload)
        val commonActivity = containerRepository.getCommonActivityByStorageCode(payload.storageCode)!!
            .map { it.toDouble() }[0]
        storageRepository.updateStorageActivity(commonActivity, payload.storageCode)
        return getContainerByContainerCode(containerCode!!)

    }

    @Transactional
    open fun updateContainer(payload: containerRequest): ContainerResponse{
        if (!containerRepository.isExistContainerByContainerCode(payload.containerCode ?: 0)) {
            throw ValidationException(33)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val containerCode = containerRepository.updateContainer(payload)
        val commonActivity = containerRepository.getCommonActivityByStorageCode(payload.storageCode)!!
            .map { it.toDouble() }[0]
        storageRepository.updateStorageActivity(commonActivity, payload.storageCode)
        return getContainerByContainerCode(containerCode!!)
    }
}

