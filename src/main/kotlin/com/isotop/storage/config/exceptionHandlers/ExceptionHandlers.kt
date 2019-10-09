package com.isotop.storage.config.exceptionHandlers

import com.isotop.storage.config.exceptionHandlers.exception.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.ws.rs.NotAuthorizedException

@RestControllerAdvice
class ExceptionHandlers {

    private val log = LoggerFactory.getLogger(ExceptionHandlers::class.java)


    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): NuclearError {
        return handleErrorCode(e)
    }

    private fun handleErrorCode(e: NuclearRuntimeException): NuclearError {
        log.error(e.errorMessage)
        return when (e.errorCode) {
            0 -> NuclearError(e.errorCode, e.errorMessage)
            else -> NuclearError(e.errorCode)
        }
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Exception): ErrorMessageResponse {
        log.error("500 Internal Server Error", e)
        return ErrorMessageResponse(true,e.message)
    }

    @ExceptionHandler(NotAuthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: NuclearRuntimeException): NuclearError {
        return handleErrorCode(e)
    }

}
