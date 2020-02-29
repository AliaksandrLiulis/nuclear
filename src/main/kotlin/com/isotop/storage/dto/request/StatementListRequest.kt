package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class StatementListRequest(
    @JsonProperty("statement_code")
    val statementCode: Int,
    @JsonProperty("storage_code")
    val storageCode: Int
)
