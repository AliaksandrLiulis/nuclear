package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.NuclideRequest
import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.repository.directory.NuclideTypesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class NuclideTypeService(
    private val nuclideTypesRepository: NuclideTypesRepository
) {
    open fun getNuclideTypes(): ListNuclideTypeDataResponse {
        return ListNuclideTypeDataResponse(nuclideTypesRepository.getNuclides())
    }

    open fun getNuclideTypeById(nuclideTypeId: Int): NuclideTypeResponse {
        if (!nuclideTypesRepository.isExistNuclideTypeById(nuclideTypeId)) {
            throw ValidationException(10)
        }
        return nuclideTypesRepository.getNuclideById(nuclideTypeId)[0]
    }

    @Transactional
    open fun addNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {
        validateAddNuclideRequest(nuclideRequest)
        return nuclideTypesRepository.addNuclide(nuclideRequest)
    }

    @Transactional
    open fun updateNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {
        validateUpdateNuclideRequest(nuclideRequest)
        return nuclideTypesRepository.updateNuclide(nuclideRequest)
    }

    @Transactional
    open fun removeNuclide(id: Int) {
        if (!nuclideTypesRepository.isExistNuclideTypeById(id)) {
            throw ValidationException(13)
        }
        return nuclideTypesRepository.removeNuclide(id)
    }

    private fun validateAddNuclideRequest(nuclideRequest: NuclideRequest) {
        if (nuclideRequest.nuclideType.isNullOrBlank()
            || nuclideTypesRepository.isExistNuclideTypeByType(nuclideRequest.nuclideType)
        ) {
            throw ValidationException(11)
        }
        if (nuclideRequest.nuclideTypeName.isNullOrBlank()
            || nuclideTypesRepository.isExistNuclideTypeByTypeName(nuclideRequest.nuclideTypeName)
        ) {
            throw ValidationException(12)
        }
    }

    private fun validateUpdateNuclideRequest(nuclideRequest: NuclideRequest) {
        if (!nuclideTypesRepository.isExistNuclideTypeById(nuclideRequest.nuclideTypeCode)) {
            throw ValidationException(13)
        }
        if (nuclideRequest.nuclideType.isNullOrBlank()
            || nuclideRequest.nuclideTypeName.isNullOrBlank()
        ) {
            throw ValidationException(14)
        }
    }
}
