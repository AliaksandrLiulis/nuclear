package com.isotop.storage.config.exceptionHandlers.exception

open class NuclearRuntimeException : RuntimeException {
    var errorCode: Int = 0
    var errorMessage: String? = null

    constructor() : super()
    constructor(errorCode: Int) {
        this.errorCode = errorCode
    }

    constructor(errorMessage: String) : super(errorMessage) {
        this.errorMessage = errorMessage
    }

    constructor(errorCode: Int, errorMessage: String) : super(errorMessage) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }
}
