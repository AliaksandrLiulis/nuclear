package com.isotop.storage.config.securityconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableAuthorizationServer
open class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Bean
    open fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(SIGNINGKEY)
        return converter
    }

    @Bean
    open fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {

        clients
            .inMemory()
            .withClient(CLIEN_ID)
            .secret(CLIENT_SECRET)
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
            .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
            .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter())
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }

    companion object {

        private val SIGNINGKEY = "as466gf"
        internal val CLIEN_ID = "client"
        internal val CLIENT_SECRET = "$2a$04$.IE4.9yvxPJ2DuUAln3mmOcgs7Fe0Ol6ZpbgQj0MH.0fJOxbUzs56"
        internal val GRANT_TYPE_PASSWORD = "password"
        internal val AUTHORIZATION_CODE = "authorization_code"
        internal val REFRESH_TOKEN = "refresh_token"
        internal val IMPLICIT = "implicit"
        internal val SCOPE_READ = "read"
        internal val SCOPE_WRITE = "write"
        internal val TRUST = "trust"
        internal val ACCESS_TOKEN_VALIDITY_SECONDS = 10 * 900 * 99000
        internal val REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 600 * 99000
    }
}
