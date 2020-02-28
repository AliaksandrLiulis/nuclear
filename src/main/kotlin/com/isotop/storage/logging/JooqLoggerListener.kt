package com.isotop.storage.logging

import org.apache.logging.log4j.LogManager
import org.jooq.ExecuteContext
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultExecuteListener

open class JooqLoggerListener : DefaultExecuteListener() {

    private val log = LogManager.getLogger()
    override fun executeStart(ctx: ExecuteContext) {

        val create = DSL.using(ctx.dialect(), Settings().withRenderFormatted(true))

        if (ctx.query() != null) {
            log.info("\n" + create.renderInlined(ctx.query()).replace("\"", ""))
        } else if (ctx.routine() != null) {
            log.info("\n" + create.renderInlined(ctx.routine()).replace("\"", ""))
        }
    }
}
