package com.isotop.storage.repository.jooq

import com.isotop.storage.config.DatasourceConfig.Companion.AUCTION_TIME_ZONE
import org.jooq.Converter
import java.time.OffsetDateTime
import java.time.ZonedDateTime

open class TimestampConverter : Converter<OffsetDateTime, ZonedDateTime> {

    override fun from(ts: OffsetDateTime?): ZonedDateTime? {
        return ts?.toInstant()?.atZone(AUCTION_TIME_ZONE)
    }

    override fun to(zonedDateTime: ZonedDateTime?): OffsetDateTime? {
        return if (zonedDateTime == null) null
        else zonedDateTime.withZoneSameInstant(AUCTION_TIME_ZONE).toOffsetDateTime()
    }

    override fun fromType(): Class<OffsetDateTime> {
        return OffsetDateTime::class.java
    }

    override fun toType(): Class<ZonedDateTime> {
        return ZonedDateTime::class.java
    }
}
