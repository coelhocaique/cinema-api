package com.coelhocaique.cinema.api.routes

import com.coelhocaique.cinema.core.util.logger
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class RoutesLoggingFilter() : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val requestId = UUID.randomUUID().toString()
        val sw = StopWatch()
        sw.start(requestId)
        logRequest(exchange)

        exchange.response.beforeCommit {
            sw.stop()
            logResponse(exchange, sw.lastTaskTimeMillis)
            Mono.empty()
        }
        return chain.filter(exchange)
    }

    private fun logRequest(exchange: ServerWebExchange) {
        logger().info("path=${exchange.request.uri.path} http_method=${exchange.request.method} stage=init")
    }

    private fun logResponse(exchange: ServerWebExchange, executionTime: Long) {
        logger().info("path=${exchange.request.uri.path} execution_time=${executionTime} http_method=${exchange.request.method} http_response_code=${exchange.response.statusCode?.value()} stage=complete")
    }
}