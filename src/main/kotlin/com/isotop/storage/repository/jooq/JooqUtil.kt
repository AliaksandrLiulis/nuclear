package com.isotop.storage.repository.jooq

import org.jooq.Field
import org.jooq.Record
import org.jooq.TableField
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import java.sql.Date

open class JooqUtil {
    companion object {
        fun dateFormat(field: TableField<out Record, Date>, format: String): Field<String> {
            return DSL.field("TO_CHAR({0}, {1})", SQLDataType.VARCHAR, field, DSL.inline(format))
        }
    }
}
