package com.isotop.storage.dto.request

import java.time.LocalDate

data class TransferRequest (
    val moutionType:Int,
    val transferDate: LocalDate,
    val orgCode: Int,
    val storageCode: Int,
    val docDate: LocalDate,
    val docNumber: String,
    val docTypeCode: Int,
    val hideEvent: Int,
    val actCode: Int
)