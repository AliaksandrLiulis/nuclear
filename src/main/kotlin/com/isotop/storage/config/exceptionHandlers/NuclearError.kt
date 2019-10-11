package com.isotop.storage.config.exceptionHandlers

data class NuclearError(val errorCode: Int, val errorMessage: String?) {

    constructor(errorCode: Int) : this(errorCode, findMessage(errorCode))

    companion object NuclearError {
        fun findMessage(errorCode: Int): String? = when (errorCode) {
            1 -> "User not found"
            2 -> "User with such name exist"
            3 -> "User with such email exist"
            4 -> "Can not update user"
            else -> ""
        }
    }
}
