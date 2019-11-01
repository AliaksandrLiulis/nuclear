package com.isotop.storage.repository

import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.jooq.Tables.DOC_TYPES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class DocRepository (
    private val dsl: DSLContext
) {

    open fun getDocTypes(): List<DocTypeResponse> {
        return dsl.select(
            DOC_TYPES.DOC_TYPE_CODE,
            DOC_TYPES.DOC_TYPE_NAME
        ).from(
            DOC_TYPES
        ).fetchInto(DocTypeResponse::class.java)
    }
}