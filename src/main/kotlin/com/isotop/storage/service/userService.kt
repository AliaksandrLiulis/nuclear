package com.isotop.storage.service

import com.isotop.storage.repository.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class userService @Autowired constructor(private val repository: Repository) {

    open fun getUserPassword(userName:String):String{
        return repository.getUserPassword(userName)
    }
}