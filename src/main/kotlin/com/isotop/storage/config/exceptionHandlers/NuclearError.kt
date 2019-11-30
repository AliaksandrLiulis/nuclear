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
            10 -> "Type by Id not exist"
            11 -> "Nuclide type not valid or exist"
            12 -> "Nuclide type name not valid or exist"
            13 -> "Nuclide type not exist"
            14 -> "Nuclide params can not be empty or null"
            15 -> "Make type not valid or exist"
            16 -> "Make type not exist"
            17 -> "Make params can not be empty or null"
            18 -> "Make type not exist"
            19 -> "Doc type not exist"
            20 -> "doc type not valid or exist"
            21 -> "doc type not exist"
            22 -> "Doc type not valid or exist"
            23 -> "Doc type not exist"
            24 -> "Doc params can not be empty or null"
            25 -> "Note to update not exist"
            26 -> "Note for source type not added"
            27 -> "Act by Id not exist"
            28 -> "Act not exist"
            29 -> "Act not valid or exist"
            30 -> "Can not execute action. Note uses other tables"
            31 -> "Container by Id not exist"
            32 -> "Storage container by Id not exist"
            33 -> "Container by Id not exist"
            34 -> "Event by Id not exist"
            35 -> "Make source by Id not exist"
            36 -> "Package by Id not exist"
            37 -> "OpenSourceUsing more than OpenSourceRest"
            else -> ""
        }
    }
}
