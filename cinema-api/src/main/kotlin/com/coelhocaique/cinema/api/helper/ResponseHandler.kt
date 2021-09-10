package com.coelhocaique.cinema.api.helper

import com.coelhocaique.cinema.api.helper.Messages.DEFAULT_ERROR_MESSAGE
import com.coelhocaique.cinema.api.helper.exception.ApiException
import com.coelhocaique.cinema.core.util.logger
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

object ResponseHandler {

    data class ErrorResponse(val error: String)

    fun <T> generateResponse(body: Mono<T>, successStatus: Int = 200, onEmptyStatus: Int = 404): Mono<ServerResponse> {
        return body.onErrorMap { it }
            .flatMap { success(it, successStatus) }
            .onErrorResume(Throwable::class.java) {
                when (it) {
                    is ApiException -> mapApiException(it)
                    else -> mapException(it)
                }
            }
            .switchIfEmpty(ServerResponse.status(onEmptyStatus).build())
    }

    private fun mapException(it: Throwable): Mono<ServerResponse> {
        logger().error("error=".plus(it.message), it)
        return ServerResponse
            .status(500)
            .bodyValue(buildErrorResponse(it.message))
    }

    private fun mapApiException(it: ApiException): Mono<ServerResponse> {
        logger().error("error=$it.type.to, cause=$it.errorMessage", it)

        return ServerResponse
            .status(it.type.status)
            .bodyValue(buildErrorResponse(it.errorMessage))
    }

    private fun <T> success(it: T, status: Int): Mono<ServerResponse> {
        return ServerResponse
            .status(status)
            .body(BodyInserters.fromValue(it!!))
    }

    private fun buildErrorResponse(message: String?) = ErrorResponse(message ?: DEFAULT_ERROR_MESSAGE)
}