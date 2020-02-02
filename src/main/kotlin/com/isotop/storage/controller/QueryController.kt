package com.isotop.storage.controller

import com.isotop.storage.dto.request.QueryRequest
import com.isotop.storage.service.QueryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sql")
open class QueryController(
    private val service: QueryService
) {

    @PostMapping(produces = ["application/json"])
    open fun executeQuery(
        @RequestBody
        payload: QueryRequest
    ): String {
        return service.executeQuery(payload.query)
    }
}
