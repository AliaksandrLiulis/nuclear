package com.isotop.storage.config.exceptionHandlers.exception

class ValidationException : NuclearRuntimeException {
    constructor() : super()
    constructor(errorCode: Int) : super(errorCode)
    constructor(errorMessage: String) : super(errorMessage)
    constructor(errorCode: Int, errorMessage: String) : super(errorCode, errorMessage)
}
