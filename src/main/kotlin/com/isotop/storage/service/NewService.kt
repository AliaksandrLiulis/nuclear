package com.isotop.storage.service

import com.isotop.storage.repository.NewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class NewService @Autowired constructor(
    private val newRepository: NewRepository
) {

    @Transactional
    open fun getUserName(password:String):String{
        return newRepository.getUserName(password)
}
}