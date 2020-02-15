package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ActRequest(
    @JsonProperty("act_code")
    val actCode: Int,
    @JsonProperty("act_date")
    val actDate: LocalDate,
    @JsonProperty("act_number")
    val actNumber: String,
    @JsonProperty("act_int_number")
    val actIntNumber: Int?,
    @JsonProperty("act_format")
    val actFormatCode: Int,
    @JsonProperty("act_note")
    val actNote: String
)