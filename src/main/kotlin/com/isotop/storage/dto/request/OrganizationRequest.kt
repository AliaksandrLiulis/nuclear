package com.isotop.storage.dto.request

data class OrganizationRequest (
    val orgCode:Int,
    val orgName: String?,
    val shortOrgName: String?,
    val iconOrgName: String?,
    val orgAddress: String?
)