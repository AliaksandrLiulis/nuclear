package com.isotop.storage.service

import com.isotop.storage.dto.response.ListMakeSourceDataResponse
import com.isotop.storage.repository.MakeSourcesRepository
import org.springframework.stereotype.Service

@Service
open class MakeSourceService(
    private val makeSourcesRepository: MakeSourcesRepository
) {

    fun getMakeSources(): ListMakeSourceDataResponse {
        return ListMakeSourceDataResponse(makeSourcesRepository.getAllMakeSources())
    }
}