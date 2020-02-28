package com.isotop.storage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.ApplicationContext

@SpringBootApplication(
    scanBasePackages = ["com.isotop.storage"]
)
@ServletComponentScan(
    basePackages = ["com.isotop.storage"]
)
@EnableConfigurationProperties
open class Service

private lateinit var applicationContext: ApplicationContext

fun main(args: Array<String>) {
    applicationContext = runApplication<Service>(*args)
}
