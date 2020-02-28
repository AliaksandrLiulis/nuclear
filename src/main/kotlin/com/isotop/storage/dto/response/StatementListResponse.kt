package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListStatementListResponse(
    val data: List<StatementListResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class StatementListResponse(
    @JsonProperty("statement_list_code")
    val statementListCode: Int,
    @JsonProperty("statement_code")
    val statementCode: Int,
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("storage_life_time")
    val storageLifeTime: Double,
    @JsonProperty("storage_category")
    val storageCategory: Int,
    @JsonProperty("passport_number")
    val passportNumber: String,
    @JsonProperty("serial_number")
    val serialNumber: String,
    @JsonProperty("activity")
    val activity: Double,
    @JsonProperty("come_date")
    val comeDate: LocalDate,
    @JsonProperty("icon_org_name")
    val iconOrgName: LocalDate
)