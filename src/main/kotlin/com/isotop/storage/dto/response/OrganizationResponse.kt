package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListOrganizationDataResponse(
    val data: List<OrganizationResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrganizationResponse(
    val orgCode: Int,
    val orgName: String?,
    val shortOrgName: String?,
    val iconOrgName: String?,
    val orgAddress: String?
)
