package com.isotop.storage.config.cors

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders.*
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
open class CORSFilter : Filter {

    @Throws(ServletException::class)
    override fun init(fc: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        req: ServletRequest,
        resp: ServletResponse,
        chain: FilterChain
    ) {

        val response = resp as HttpServletResponse
        val request = req as HttpServletRequest

        val headers = ALLOWED_HEADERS.joinToString(",")

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "PATCH,POST,GET,OPTIONS,DELETE,PUT")
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600")
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, headers)

        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, resp)
        }
    }

    override fun destroy() {}

    companion object {

        private val ALLOWED_HEADERS = arrayOf(
            "x-requested-with",
            "authorization",
            "Content-Type",
            "Authorization",
            "credential",
            "X-XSRF-TOKEN",
            "X-CSRF-TOKEN"
        )
    }
}
