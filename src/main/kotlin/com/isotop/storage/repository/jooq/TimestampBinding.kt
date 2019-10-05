package com.isotop.storage.repository.jooq

import org.jooq.*
import org.jooq.impl.DefaultBinding
import java.sql.SQLException
import java.time.OffsetDateTime
import java.time.ZonedDateTime

open class TimestampBinding : Binding<OffsetDateTime, ZonedDateTime> {

    private val CONVERTER = TimestampConverter()

    private val delegate = DefaultBinding(converter())

    final override fun converter(): Converter<OffsetDateTime, ZonedDateTime> {
        return CONVERTER
    }

    override fun sql(ctx: BindingSQLContext<ZonedDateTime>) {
        delegate.sql(ctx)
    }

    @Throws(SQLException::class)
    override fun register(ctx: BindingRegisterContext<ZonedDateTime>) {
        delegate.register(ctx)
    }

    @Throws(SQLException::class)
    override fun set(ctx: BindingSetStatementContext<ZonedDateTime>) {
        delegate.set(ctx)
    }

    @Throws(SQLException::class)
    override fun set(ctx: BindingSetSQLOutputContext<ZonedDateTime>) {
        delegate.set(ctx)
    }

    @Throws(SQLException::class)
    override fun get(ctx: BindingGetResultSetContext<ZonedDateTime>) {
        delegate.get(ctx)
    }

    @Throws(SQLException::class)
    override fun get(ctx: BindingGetStatementContext<ZonedDateTime>) {
        delegate.get(ctx)
    }

    @Throws(SQLException::class)
    override fun get(ctx: BindingGetSQLInputContext<ZonedDateTime>) {
        delegate.get(ctx)
    }

}
