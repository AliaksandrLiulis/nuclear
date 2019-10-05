package com.isotop.storage.config

import com.isotop.storage.logging.JooqLoggerListener
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.simpleflatmapper.jooq.SfmRecordMapperProviderFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import java.time.ZoneOffset
import java.time.ZoneOffset.UTC
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties
open class DatasourceConfig {

    @Value("\${spring.datasource.url}")
    private lateinit var dbUrl: String

    @Value("\${nuclear.logging.jooq.enabled}")
    private var enableJooqLogging: Boolean = false

    @Value("\${spring.datasource.username:#{null}}")
    private var dbUserName: String? = null

    @Value("\${spring.datasource.password:#{null}}")
    private var dbPassword: String? = null

    @Bean
    open fun dataSource(): DataSource = HikariDataSource(hikariConfig())

    private fun hikariConfig(): HikariConfig {
        val config = HikariConfig()
        config.schema = "public"
        config.jdbcUrl = dbUrl
        config.username = dbUserName
        config.password =dbPassword
        return config
    }

    @Bean
    open fun connectionProvider(): DataSourceConnectionProvider = DataSourceConnectionProvider(
        TransactionAwareDataSourceProxy(dataSource()))

    @Bean
    open fun dsl(): DefaultDSLContext = DefaultDSLContext(jooqConfiguration())

    private fun jooqConfiguration(): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES)
        jooqConfiguration.setConnectionProvider(connectionProvider())
        if (enableJooqLogging) {
            jooqConfiguration.setExecuteListenerProvider(DefaultExecuteListenerProvider(JooqLoggerListener()))
        }

        jooqConfiguration.set(
            SfmRecordMapperProviderFactory.newInstance()
            .ignorePropertyNotFound()
            .newProvider())
        return jooqConfiguration
    }

    companion object {
        val AUCTION_TIME_ZONE: ZoneOffset = UTC
    }
}
