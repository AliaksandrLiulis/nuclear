package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.NuclideRequest
import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.repository.NuclideRepository
import org.springframework.stereotype.Service

@Service
open class NuclideService(
    private val nuclideRepository: NuclideRepository
) {
    open fun getNuclideTypes(): ListNuclideTypeDataResponse {
        return ListNuclideTypeDataResponse(nuclideRepository.getNuclides())
    }

    open fun getNuclideTypeById(nuclideTypeId: Int): NuclideTypeResponse {
        if (!nuclideRepository.isExistNuclideTypeById(nuclideTypeId)) {
            throw ValidationException(10)
        }
        return nuclideRepository.getNuclideById(nuclideTypeId)[0]
    }

    open fun addNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {
        validateAddNuclideRequest(nuclideRequest)
        return nuclideRepository.addNuclide(nuclideRequest)
    }

    open fun updateNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {
        validateUpdateNuclideRequest(nuclideRequest)
        return nuclideRepository.updateNuclide(nuclideRequest)
    }

    open fun removeNuclide(id: Int) {
        if (!nuclideRepository.isExistNuclideTypeById(id)) {
            throw ValidationException(13)
        }
        return nuclideRepository.removeNuclide(id)
    }

    private fun validateAddNuclideRequest(nuclideRequest: NuclideRequest) {
        if (nuclideRequest.nuclideType.isNullOrBlank()
            || nuclideRepository.isExistNuclideTypeByType(nuclideRequest.nuclideType)
        ) {
            throw ValidationException(11)
        }
        if (nuclideRequest.nuclideTypeName.isNullOrBlank()
            || nuclideRepository.isExistNuclideTypeByTypeName(nuclideRequest.nuclideTypeName)
        ) {
            throw ValidationException(12)
        }
    }

    private fun validateUpdateNuclideRequest(nuclideRequest: NuclideRequest) {
        if (!nuclideRepository.isExistNuclideTypeById(nuclideRequest.nuclideTypeCode)) {
            throw ValidationException(13)
        }
        if (nuclideRequest.nuclideType.isNullOrBlank()
            || nuclideRequest.nuclideTypeName.isNullOrBlank()
        ) {
            throw ValidationException(14)
        }
    }
}
