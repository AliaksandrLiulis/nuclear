package com.isotop.storage.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
open class MainController {

    open fun start(): String {
        return "index"
    }
}
