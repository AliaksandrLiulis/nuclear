package com.isotop.storage.repository.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.DocTypeRequest
import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.jooq.Tables.DOC_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class DocTypeRepository(
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

    open fun getDocTypeById(typeId: Int): List<DocTypeResponse> {
        return dsl.select(
            DOC_TYPES.DOC_TYPE_CODE,
            DOC_TYPES.DOC_TYPE_NAME
        ).from(
            DOC_TYPES
        )
            .where(
                DOC_TYPES.DOC_TYPE_CODE.eq(typeId)
            ).fetchInto(DocTypeResponse::class.java)
    }

    open fun addDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {

        val docId = dsl.insertInto(DOC_TYPES)
            .columns(
                DOC_TYPES.DOC_TYPE_NAME
            )
            .values(
                docTypeRequest.docTypeName
            ).onDuplicateKeyUpdate()
            .set(DOC_TYPES.DOC_TYPE_NAME, docTypeRequest.docTypeName)
            .returning(DOC_TYPES.DOC_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(DOC_TYPES.DOC_TYPE_CODE)

        return getDocTypeById(docId!!)[0]
    }

    open fun updateDocType(docTypeRequest: DocTypeRequest): DocTypeResponse {

        val updateValues = mapOf<Any, Any?>(
            DOC_TYPES.DOC_TYPE_NAME to docTypeRequest.docTypeName
        )

        val docId = dsl
            .update(DOC_TYPES)
            .set(updateValues)
            .where(
                DOC_TYPES.DOC_TYPE_CODE.eq(docTypeRequest.docTypeCode)
            )
            .returning(DOC_TYPES.DOC_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(DOC_TYPES.DOC_TYPE_CODE)

        return getDocTypeById(docId!!)[0]

    }

    open fun removeDocType(id: Int) {
        try {
            dsl.delete(
                DOC_TYPES
            ).where(
                DOC_TYPES.DOC_TYPE_CODE.eq(id)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistDocTypeById(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(DOC_TYPES.DOC_TYPE_CODE)
                .from(DOC_TYPES)
                .where(DOC_TYPES.DOC_TYPE_CODE.eq(typeId))
        )
    }

    open fun isExistDocTypeByName(type: String): Boolean {
        return dsl.fetchExists(
            DSL.select(DOC_TYPES.DOC_TYPE_CODE)
                .from(DOC_TYPES)
                .where(DOC_TYPES.DOC_TYPE_NAME.equalIgnoreCase(type))
        )
    }
}