package com.isotop.storage.config.securityconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
open class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private val tokenStore: TokenStore? = null

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID).stateless(false)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
    }

    @Bean
    @Primary
    open fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore)
        return defaultTokenServices
    }

    companion object {
        private val RESOURCE_ID = "resource_id"
    }
}