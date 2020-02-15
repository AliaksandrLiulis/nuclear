package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OrganizationRequest (
    val orgCode:Int,
    @JsonProperty("org_name")
    val orgName: String?,
    @JsonProperty("short_org_name")
    val shortOrgName: String?,
    @JsonProperty("icon_org_name")
    val iconOrgName: String?,
    @JsonProperty("org_address")
    val orgAddress: String?
)
