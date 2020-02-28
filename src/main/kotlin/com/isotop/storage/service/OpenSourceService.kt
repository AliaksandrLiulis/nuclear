package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.OpenSourceUsingRequest
import com.isotop.storage.repository.ContainerRepository
import com.isotop.storage.repository.PackageRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class OpenSourceService(
    private val containerService: ContainerService,
    private val containerRepository: ContainerRepository,
    private val packageService: PackageService,
    private val storageRepository: StorageRepository,
    private val packageRepository: PackageRepository
) {

    @Transactional
    open fun updateOpenSourceUsing(payload: OpenSourceUsingRequest) {
        val container = containerService.getContainerByContainerCode(payload.containerCode)
        if (container.openSourceRest < payload.openSourceUsing) {
            throw ValidationException(37)
        }
        if (!storageRepository.isExistStorageNoteById(payload.storageCode)) {
            throw ValidationException(31)
        }
        containerRepository.updateOpenSourceRestInContainer(
            (container.openSourceRest - payload.openSourceUsing),
            payload.containerCode
        )
        packageRepository.addPackage(payload)
        packageService.updateDataStorageAfterChangesInPackage(payload.storageCode)
    }
}
