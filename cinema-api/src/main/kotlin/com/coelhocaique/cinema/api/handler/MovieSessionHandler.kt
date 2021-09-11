package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.helper.FetchCriteria.SearchType.DATE_TIME
import com.coelhocaique.cinema.api.helper.RequestParameterHandler.extractBody
import com.coelhocaique.cinema.api.helper.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.helper.RequestParameterHandler.retrieveMovieId
import com.coelhocaique.cinema.api.helper.LinkBuilder.addMovieSessionResponseLinks
import com.coelhocaique.cinema.api.helper.RequestParameterHandler.retrieveSessionParameters
import com.coelhocaique.cinema.api.helper.RequestValidator.validate
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.service.session.MovieSessionRequest
import com.coelhocaique.cinema.core.service.session.MovieSessionService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MovieSessionHandler(private val movieSessionService: MovieSessionService) {

    fun findByMovieId(req: ServerRequest): Mono<ServerResponse> {
        return retrieveSessionParameters(req)
            .flatMap {
                when (it.searchType()) {
                    DATE_TIME -> movieSessionService.find(it.movieId!!, it.dateTime!!)
                    else -> movieSessionService.find(it.movieId!!)
                }
            }
            .flatMap { addMovieSessionResponseLinks(req, it) }
            .let { generateResponse(it, onEmptyStatus = HttpStatus.OK) }
    }

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return extractBody<MovieSessionRequest>(req)
            .flatMap { validate(it).zipWith(retrieveMovieId(req)) }
            .flatMap { movieSessionService.create(it.t2, it.t1) }
            .flatMap { addMovieSessionResponseLinks(req, it) }
            .let { generateResponse(it, successStatus = HttpStatus.CREATED) }
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        return retrieveMovieId(req).zipWith(retrieveId(req))
            .flatMap { movieSessionService.delete(it.t1, it.t2) }
            .let { generateResponse(it, successStatus = HttpStatus.NO_CONTENT) }
    }
}