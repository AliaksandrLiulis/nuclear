package com.isotop.storage.service

import com.isotop.storage.repository.QueryRepository
import jdk.nashorn.internal.parser.JSONParser
import org.apache.coyote.http11.Constants.a
import org.bouncycastle.asn1.x500.style.RFC4519Style.c
import org.jooq.tools.json.JSONArray
import org.jooq.tools.json.JSONObject
import org.jooq.tools.json.JSONValue
import org.springframework.stereotype.Service
import java.util.function.BiConsumer

@Service
open class QueryService(
    private val repository: QueryRepository
) {
    private val BASE_QUERY = "select * from "
    private val TABLE = "table"
    private val WHERE = "where"
    private val AND = "and"

    open fun executeQuery(payload: String): String {
        return repository.executeQuery(payload)
    }

    open fun executeQueryWithParams(payload: JSONObject): String {
        val query = BASE_QUERY + payload[TABLE]
        val params = payload[WHERE]
        if (params != null) {
            val z = params as MutableMap<*, *>
            var whereQuery = " where"
            z.forEach { t, u ->
                whereQuery = if (u !is String) {
                    "$whereQuery $t = $u $AND"
                } else {
                    "$whereQuery $t = '$u' $AND"
                }
            }
            val resultQuery = query + whereQuery
            return executeQuery(resultQuery.substring(0, resultQuery.length - 4))
        }
        return executeQuery(query)
    }
}
