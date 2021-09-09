package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveMovieId
import com.coelhocaique.cinema.api.helper.RequestValidator.validate
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.domain.session.MovieSessionRequest
import com.coelhocaique.cinema.core.domain.session.MovieSessionService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MovieSessionHandler(private val movieSessionService: MovieSessionService) {

    fun findByMovieId(req: ServerRequest): Mono<ServerResponse> {
        return retrieveMovieId(req)
            .flatMap { movieSessionService.findByMovieId(it) }
            .let { generateResponse(it) }
    }

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return extractBody<MovieSessionRequest>(req)
            .flatMap { validate(it).zipWith(retrieveMovieId(req)) }
            .flatMap { movieSessionService.create(it.t2, it.t1) }
            .let { generateResponse(it, successStatus = HttpStatus.CREATED.value()) }
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        return retrieveMovieId(req).zipWith(retrieveId(req))
            .flatMap { movieSessionService.delete(it.t2, it.t2) }
            .let { generateResponse(it, onEmptyStatus = HttpStatus.NOT_FOUND.value(), successStatus = HttpStatus.NO_CONTENT.value()) }
    }
}