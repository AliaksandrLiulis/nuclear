package com.isotop.storage.service

import com.isotop.storage.repository.QueryRepository
import org.springframework.stereotype.Service

@Service
open class QueryService(
    private val repository: QueryRepository
) {
    open fun executeQuery(payload: String): String {
        return repository.executeQuery(payload)
    }
}
