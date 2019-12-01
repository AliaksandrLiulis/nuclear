package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListPackagesDataResponse
import com.isotop.storage.repository.ContainerRepository
import com.isotop.storage.repository.PackageRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
open class PackagesService(
    private val packageRepository: PackageRepository,
    private val containerService: ContainerService,
    private val containerRepository: ContainerRepository,
    private val storageRepository: StorageRepository
) {

    open fun getPackagesByStorageCode(storageCode: Int): ListPackagesDataResponse {
        return ListPackagesDataResponse(packageRepository.getPackagesByStorageCode(storageCode))
    }

    @Transactional
    open fun removePackageById(packageId: Int) {
        if (!packageRepository.isExistPackageByPackageCode(packageId)) {
            throw ValidationException(36)
        }
        val packages = packageRepository.getPackageByPackageCode(packageId)
        val containers = containerService.getContainerByContainerCode(packages.containerCode)
        containerRepository.updateOpenSourceRestInContainer(
            (containers.openSourceCount + packages.openSourceUsing),
            packages.containerCode
        )
        val commonActivity = packageRepository.getCommonActivityByStorageCode(packages.storageCode)
        packageRepository.removePackage(packageId)
        storageRepository.updateStorageActivity(commonActivity, packages.storageCode)
    }
}