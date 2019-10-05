package com.isotop.storage

import by.delta.service.impl.UserServiceImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication(
    scanBasePackages = ["com.isotop.storage"])
@ServletComponentScan(
    basePackages = ["com.isotop.storage"])
@EnableConfigurationProperties
open class Service

private lateinit var applicationContext: ApplicationContext

fun main(args: Array<String>) {
    applicationContext = runApplication<Service>(*args)
}

@Bean
@Qualifier(value = "developerService")
fun getService():UserServiceImpl{
    return UserServiceImpl(BCryptPasswordEncoder())
}
