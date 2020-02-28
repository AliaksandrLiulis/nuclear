package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListStatementResponse(
    val data: List<StatementResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StatementResponse(
    @JsonProperty("statement_code")
    val statementCode: Int,
    @JsonProperty("statement_type")
    val statementType: Int,
    @JsonProperty("statement_date")
    val statementDate: LocalDate,
    @JsonProperty("statement_number")
    val statementNumber: String,
    @JsonProperty("statement_int_number")
    val statementIntNumber: Int,
    @JsonProperty("statement_note")
    val statementNote: String,
    @JsonProperty("contains_uranium")
    val containsUranium: Boolean
)
