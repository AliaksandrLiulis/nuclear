package com.isotop.storage.config.exceptionHandlers

data class NuclearError(val errorCode: Int, val errorMessage: String?) {

    constructor(errorCode: Int) : this(errorCode, findMessage(errorCode))

    companion object NuclearError {
        fun findMessage(errorCode: Int): String? = when (errorCode) {
            1 -> "User not found"
            2 -> "User with such name exist"
            3 -> "User with such email exist"
            4 -> "Can not update user"
            5 -> "Organization name not valid or exist"
            6 -> "Short organization name not valid or exist"
            7 -> "Icon organization name not valid or exist"
            8 -> "Organization not exist"
            9 -> "Organization params can not be empty or null"
            else -> ""
        }
    }
}
