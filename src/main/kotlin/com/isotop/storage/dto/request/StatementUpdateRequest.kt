package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class StatementUpdateRequest (
    @JsonProperty("statement_code")
    val statementCode: Int,
    @JsonProperty("statement_type")
    val statementType: Int,
    @JsonProperty("statement_date")
    val statementDate: LocalDate,
    @JsonProperty("statement_number")
    val statementNumber: String,
    @JsonProperty("statement_int_number")
    val statementIntNumber: Int? = 0,
    @JsonProperty("statement_note")
    val statementNote: String,
    @JsonProperty("contains_uranium")
    val containsUranium: Boolean
)
