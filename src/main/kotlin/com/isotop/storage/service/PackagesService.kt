package com.isotop.storage.service

import com.isotop.storage.dto.response.ListPackagesDataResponse
import com.isotop.storage.repository.PackageRepository
import org.springframework.stereotype.Service

@Service
open class PackagesService(
    private val packageRepository: PackageRepository
) {

    open fun getPackagesByStorageCode(storageCode: Int): ListPackagesDataResponse {
        return ListPackagesDataResponse(packageRepository.getPackagesByStorageCode(storageCode))
    }
}