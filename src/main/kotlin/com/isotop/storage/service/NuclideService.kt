package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.repository.NuclideRepository
import org.springframework.stereotype.Service

@Service
open class NuclideService(
    private val nuclideTypeRepository: NuclideRepository
) {
    open fun getNuclideTypes(): ListNuclideTypeDataResponse {
        return ListNuclideTypeDataResponse(nuclideTypeRepository.getNuclideTypes())
    }

    open fun getNuclideTypeById(nuclideTypeId: Int): NuclideTypeResponse {
        if (!nuclideTypeRepository.isExistNuclideType(nuclideTypeId)) {
            throw ValidationException(10)
        }
        return nuclideTypeRepository.getNuclideTypeById(nuclideTypeId)[0]
    }
}
