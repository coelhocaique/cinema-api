package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.helper.LinkBuilder.addMovieResponseLinks
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.service.movie.MovieService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MovieHandler(private val movieService: MovieService) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrieveId(req)
            .flatMap { movieService.findById(it) }
            .flatMap { addMovieResponseLinks(req, it) }
            .let { generateResponse(it) }
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        return movieService.findAll()
            .flatMap { addMovieResponseLinks(req, it)}
            .let { generateResponse(it, onEmptyStatus = HttpStatus.OK.value()) }
    }
}