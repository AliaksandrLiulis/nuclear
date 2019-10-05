package com.isotop.storage.controller

import com.isotop.storage.dto.User
import com.isotop.storage.service.NewService
import com.isotop.storage.service.userService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/accounts")
@Validated
open class UserController @Autowired constructor(
    private val service: userService,
    private val newService: NewService
) {

   @GetMapping("/{accountId}")
    open fun getAccount(
        @PathVariable(value = "accountId")
        accountId: User
    ): String {

        return service.getUserPassword("slv")
    }

    @GetMapping("get")
    open fun get() : String{
        val password:String = "123"
        return newService.getUserName(password)
    }
}