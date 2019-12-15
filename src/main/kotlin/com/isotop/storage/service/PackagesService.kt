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
    private val containerRepository: ContainerRepository,
    private val storageRepository: StorageRepository,
    private val storageService: StorageService

) {

    open fun getPackagesByStorageCode(storageCode: Int): ListPackagesDataResponse {
        return ListPackagesDataResponse(packageRepository.getPackagesByStorageCode(storageCode))
    }

    @Transactional
    open fun removePackageById(packageId: Int) {
        if (!packageRepository.isExistPackageByPackageCode(packageId)) {
            throw ValidationException(36)
        }
        val packageByCode = packageRepository.getPackageByPackageCode(packageId)
        val containerActivity = storageService.getActivityByStorageCode(packageByCode.storageCode)
        val containerByContainerCode =
            containerRepository.getContainersByContainerCode(packageByCode.containerCode)
        containerRepository.updateOpenSourceRestInContainer(
            (containerByContainerCode.openSourceCount + packageByCode.openSourceUsing),
            packageByCode.containerCode
        )
        packageRepository.removePackage(packageId)
        storageRepository.updateStorageActivity(
            containerActivity - (packageByCode.souceActivity * packageByCode.openSourceUsing),
            packageByCode.storageCode)
    }
}