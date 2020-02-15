package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListOrganizationDataResponse(
    val data: List<OrganizationResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrganizationResponse(
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("org_name")
    val orgName: String?,
    @JsonProperty("short_org_name")
    val shortOrgName: String?,
    @JsonProperty("icon_org_name")
    val iconOrgName: String?,
    @JsonProperty("org_address")
    val orgAddress: String?
)
