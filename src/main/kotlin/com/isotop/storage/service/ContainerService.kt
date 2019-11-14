package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.AddContainerRequest
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

    open fun getContainersByStorageCode(idStorageCode: Int): ListContainerDataResponse {
        if (!storageRepository.isExistStorageContainerNoteById(idStorageCode)) {
            throw ValidationException(32)
        }
        if (!containerRepository.isExistContainerByStorageCode(idStorageCode)) {
            return ListContainerDataResponse(listOf())
        }
        return ListContainerDataResponse(containerRepository.getListContainerByStorageCode(idStorageCode))
    }

    @Transactional
    open fun addContainer(payload: AddContainerRequest):ListContainerDataResponse {
        if (!storageRepository.isExistStorageContainerNoteById(payload.storageCode)) {
            throw ValidationException(32)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val commonActivity = containerRepository.addContainerAndGetCommonActivity(payload)!!.map { it.toDouble() }[0]
        storageRepository.updateStorageActivity(commonActivity, payload.storageCode)
        return getAllFromContainer()
    }

    @Transactional
    open fun updateContainer(payload: AddContainerRequest) {
        if (!containerRepository.isExistContainerByContainerCode(payload.containerCode?:0)) {
            throw ValidationException(33)
        }
        payload.openSourceActivity = payload.sourceActivity / payload.openSourceCount
        payload.openSourceRest = payload.openSourceCount
        val commonActivity = containerRepository.updateContainerAndGetCommonActivity(payload)!!.map { it.toDouble() }[0]
        storageRepository.updateStorageActivity(commonActivity, payload.storageCode)
    }
}

