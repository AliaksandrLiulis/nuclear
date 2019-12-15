package com.isotop.storage.service

import com.isotop.storage.dto.response.ListAvailableSourceDataResponse
import com.isotop.storage.repository.ContainerRepository
import org.springframework.stereotype.Service

@Service
open class SourceService(
    private val containerRepository: ContainerRepository
) {

    open fun getAvailableSources(): ListAvailableSourceDataResponse {
        return ListAvailableSourceDataResponse(containerRepository.getAvailableSources())
    }
}
