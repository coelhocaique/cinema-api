package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.domain.movie.MovieService
import com.coelhocaique.cinema.core.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class MovieHandler(private val service: MovieService) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrieveId(req)
                .flatMap { service.findById(it) }
                .let { generateResponse(it) }
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        return generateResponse(service.findAll())
    }
}