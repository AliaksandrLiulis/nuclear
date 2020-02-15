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
    private val START_DATE = "start_date"
    private val END_DATE = "end_date"

    open fun executeQuery(payload: String): String {
        return repository.executeQuery(payload)
    }

    open fun executeQueryWithParams(payload: JSONObject): String {
        if (payload[TABLE] == REPORT_NUCLEAR) {
            return executeQuery(getReportNuclearMoveQuery(payload))
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

    private fun getReportNuclearMoveQuery(payload: JSONObject): String {
        val startDate = payload[START_DATE]
        val endDate = payload[END_DATE]
        return "SELECT * FROM moutions A" +
                " left outer join storages B On(A.storage_code=B.storage_code)" +
                "where A.moution_date BETWEEN '$startDate' AND '$endDate'" +
                "order by A.moution_date"
    }
}
