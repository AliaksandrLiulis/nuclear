package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ContainerRequest
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

    open fun getContainerByStorageCode(storageCode: Int): ListContainerDataResponse {
        if (!containerRepository.isExistContainerByStorageCode(storageCode)) {
            throw ValidationException(31)
        }
        if (!storageRepository.isExistStorageContainerNoteById(storageCode)) {
            throw ValidationException(32)
        }

        return ListContainerDataResponse(containerRepository.getContainerByStorageCode(storageCode))
    }

    open fun getContainerByContainerCode(containerCode: Int): ContainerResponse {
        if (!containerRepository.isExistContainerByContainerCode(containerCode)) {
            throw ValidationException(33)
        }
        return containerRepository.getContainerByContainerCode(containerCode)
    }

    @Transactional
    open fun addContainer(payload: ContainerRequest): ContainerResponse {
        if (!storageRepository.isExistStorageContainerNoteById(payload.storageCode)) {
            throw ValidationException(32)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val containerCode = containerRepository.addContainerAndGetCommonActivity(payload)
        updateDataStorageAfterChanges(payload.storageCode)
        return getContainerByContainerCode(containerCode!!)

    }

    @Transactional
    open fun updateContainer(payload: ContainerRequest): ContainerResponse{
        if (!containerRepository.isExistContainerByContainerCode(payload.containerCode ?: 0)) {
            throw ValidationException(33)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val containerCode = containerRepository.updateContainer(payload)
        updateDataStorageAfterChanges(payload.storageCode)
        return getContainerByContainerCode(containerCode!!)
    }

    @Transactional
    open fun removeCotainer(containerCode: Int) {
        if (!containerRepository.isExistContainerByContainerCode(containerCode)) {
            throw ValidationException(31)
        }
        val storageCode = containerRepository.getStorageCodeByContainerCode(containerCode)
        containerRepository.removeContainer(storageCode, containerCode)
        updateDataStorageAfterChanges(storageCode)
    }

    private fun updateDataStorageAfterChanges(storageCode: Int){
        val commonActivity = containerRepository.getCommonActivityByStorageCode(storageCode)!!
            .map { it.toDouble() }[0]
        storageRepository.updateStorageActivity(commonActivity, storageCode)
    }
}

