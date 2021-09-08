package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.helper.Fields.ID
import com.coelhocaique.cinema.api.helper.Messages.INVALID_ID
import com.coelhocaique.cinema.api.helper.Messages.INVALID_REQUEST
import com.coelhocaique.cinema.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.cinema.core.util.formatToUUID
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.UUID

object RequestParameterHandler {

    inline fun <reified T> extractBody(req: ServerRequest): Mono<T> {
        return req.bodyToMono(T::class.java)
                .onErrorMap { business(INVALID_REQUEST, it) }
    }

    fun retrieveId(req: ServerRequest): Mono<UUID> {
        return try {
            just(formatToUUID(req.pathVariable(ID)))
        } catch (e: Exception) {
            throw business(INVALID_ID)
        }
    }

}