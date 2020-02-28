package com.isotop.storage.config.exceptionHandlers

import com.isotop.storage.config.exceptionHandlers.exception.ErrorMessageResponse
import com.isotop.storage.config.exceptionHandlers.exception.NuclearRuntimeException
import com.isotop.storage.config.exceptionHandlers.exception.ResourceNotFoundException
import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException
import javax.ws.rs.NotAuthorizedException

@RestControllerAdvice
class ExceptionHandlers {

    private val log = LoggerFactory.getLogger(ExceptionHandlers::class.java)

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): NuclearError {
        return handleErrorCode(e)
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidateException(e: ValidationException): NuclearError {
        return handleErrorCode(e)
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(e: BindException): ErrorMessageResponse {
       return createErrorValidationMessage(e.bindingResult)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Exception): ErrorMessageResponse {
        log.error("500 Internal Server Error", e)
        return ErrorMessageResponse("undefined", e.message)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, NumberFormatException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleServiceExceptions(): ErrorMessageResponse {
        return ErrorMessageResponse("undefined", null)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorMessageResponse {
        return createErrorValidationMessage(e.bindingResult)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun exceptionHandler(e: ConstraintViolationException): ErrorMessageResponse {
        return ErrorMessageResponse("undefined", e.message)
    }

    @ExceptionHandler(NotAuthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: NuclearRuntimeException): NuclearError {
        return handleErrorCode(e)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorMessageResponse {
        return ErrorMessageResponse("undefined", e.message)
    }

    private fun handleErrorCode(e: NuclearRuntimeException): NuclearError {
        log.error(e.errorMessage)
        return when (e.errorCode) {
            0 -> NuclearError(e.errorCode, e.errorMessage)
            else -> NuclearError(e.errorCode)
        }
    }

    private fun createErrorValidationMessage(bindingResult: BindingResult): ErrorMessageResponse {
        val errorMessage = bindingResult.allErrors
            .map { it.defaultMessage }
            .joinToString(";")
        return ErrorMessageResponse("undefined", errorMessage)
    }
}
