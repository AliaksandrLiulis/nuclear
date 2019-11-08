package com.isotop.storage.service.directory

import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.repository.directory.MoutionTypesRepository
import org.springframework.stereotype.Service

@Service
open class MoutionTypesService(
    private val moutionTypesTypesRepository: MoutionTypesRepository
) {
    open fun getMoutionTypes(): ListMoutionTypeDataResponse {
        return ListMoutionTypeDataResponse(moutionTypesTypesRepository.getMoutionTypes())
    }


}
