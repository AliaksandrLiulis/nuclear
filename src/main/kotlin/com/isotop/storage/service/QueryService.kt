package com.isotop.storage.service

import com.isotop.storage.repository.QueryRepository
import org.jooq.tools.json.JSONObject
import org.springframework.stereotype.Service

@Service
open class QueryService(
    private val repository: QueryRepository
) {
    private val BASE_QUERY = "select * from "
    private val TABLE = "table"
    private val WHERE = "where"
    private val AND = "and"
    private val REPORT_NUCLEAR = "report_nuclear_move"

    open fun executeQuery(payload: String): String {
        return repository.executeQuery(payload)
    }

    open fun executeQueryWithParams(payload: JSONObject): String {
        if (payload[TABLE] == REPORT_NUCLEAR) {
            getReportNuclearMoveQuery(payload)
        }
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

    private fun getReportNuclearMoveQuery(payload: JSONObject) {

    }
}
