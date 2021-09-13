package com.coelhocaique.cinema.api.helper

import com.coelhocaique.cinema.api.helper.Messages.DEFAULT_ERROR_MESSAGE
import com.coelhocaique.cinema.api.helper.exception.ApiException
import com.coelhocaique.cinema.core.util.exception.CoreException
import com.coelhocaique.cinema.core.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

object ResponseHandler {

    data class ErrorResponse(val error: String)

    fun <T> generateResponse(body: Mono<T>, successStatus: HttpStatus = OK, onEmptyStatus: HttpStatus = BAD_REQUEST): Mono<ServerResponse> {
        return body.onErrorMap { it }
            .flatMap { success(it, successStatus) }
            .onErrorResume(Throwable::class.java) {
                when (it) {
                    is ApiException -> mapApiException(it)
                    is CoreException -> mapCoreException(it)
                    else -> mapException(it)
                }
            }
            .switchIfEmpty(ServerResponse.status(onEmptyStatus).build())
    }

    private fun mapException(it: Throwable): Mono<ServerResponse> {
        logger().error("error=".plus(it.message), it)
        return ServerResponse
            .status(INTERNAL_SERVER_ERROR)
            .body(BodyInserters.fromObject(buildErrorResponse(it.message)))
    }

    private fun mapApiException(it: ApiException): Mono<ServerResponse> {
        logger().error("error=$it.type.to, cause=$it.errorMessage")

        return ServerResponse
            .status(it.type.status)
            .body(BodyInserters.fromObject(buildErrorResponse(it.errorMessage)))
    }

    private fun mapCoreException(it: CoreException): Mono<ServerResponse> {
        logger().error("error=${it.errorMessage}")

        return ServerResponse
            .status(BAD_REQUEST)
            .body(BodyInserters.fromObject(buildErrorResponse(it.errorMessage)))
    }

    private fun <T> success(it: T, status: HttpStatus): Mono<ServerResponse> {
        return ServerResponse
            .status(status)
            .body(BodyInserters.fromObject(it!!))
    }

    private fun buildErrorResponse(message: String?) = ErrorResponse(message ?: DEFAULT_ERROR_MESSAGE)
}