package com.coelhocaique.cinema.api.helper

import com.coelhocaique.cinema.api.helper.Fields.DATE_TIME
import com.coelhocaique.cinema.api.helper.Fields.ID
import com.coelhocaique.cinema.api.helper.Fields.MOVIE_ID
import com.coelhocaique.cinema.api.helper.Messages.INVALID_PATH_PARAM
import com.coelhocaique.cinema.api.helper.Messages.INVALID_REQUEST
import com.coelhocaique.cinema.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.cinema.core.util.formatToUUID
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.UUID

object RequestParameterHandler {

    inline fun <reified T> extractBody(req: ServerRequest): Mono<T> {
        return req.bodyToMono(T::class.java)
            .onErrorMap { business(INVALID_REQUEST, it) }
    }

    fun retrieveId(req: ServerRequest): Mono<UUID> {
        return just(retrievePathUUID(req, ID))
    }

    fun retrieveMovieId(req: ServerRequest): Mono<UUID> {
        return just(retrievePathUUID(req, MOVIE_ID))
    }

    fun retrieveSessionParameters(req: ServerRequest): Mono<FetchCriteria> {
        return just(
            FetchCriteria(
                movieId = retrievePathUUID(req, MOVIE_ID),
                dateTime = retrieveParamDateTime(req, DATE_TIME)
            )
        )
    }

    private fun retrievePathUUID(req: ServerRequest, param: String): UUID {
        try {
            return formatToUUID(req.pathVariable(param))
        } catch (e: Exception) {
            throw business(INVALID_PATH_PARAM.format(param))
        }
    }

    private fun retrieveParamDateTime(req: ServerRequest, param: String): LocalDateTime? {
        try {
            return req.queryParam(param)
                .filter { it.isNotBlank() }
                .map { LocalDateTime.parse(it) }
                .orElse(null)
        } catch (e: Exception) {
            throw business(INVALID_PATH_PARAM.format(param))
        }
    }
}