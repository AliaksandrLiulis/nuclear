package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListEventDataResponse
import com.isotop.storage.service.EventService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
open class EventController(
    private val eventService: EventService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getEvents(): ListEventDataResponse {
        return eventService.getAllActiveAvent()
    }
}