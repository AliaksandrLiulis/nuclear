package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class TransferRequest(
    @JsonProperty("moution_type")
    val moutionType: Int,
    @JsonProperty("transfer_date")
    val transferDate: LocalDate,
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("doc_date")
    val docDate: LocalDate,
    @JsonProperty("doc_number")
    val docNumber: String,
    @JsonProperty("doc_type_code")
    val docTypeCode: Int,
    @JsonProperty("hide_event")
    val hideEvent: Int,
    @JsonProperty("act_code")
    val actCode: Int
)
