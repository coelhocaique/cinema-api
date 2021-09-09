package com.coelhocaique.cinema.api.helper

import com.coelhocaique.cinema.core.service.movie.MovieResponse
import com.coelhocaique.cinema.core.service.review.ReviewResponse
import com.coelhocaique.cinema.core.service.session.MovieSessionResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

object LinkBuilder {

    fun addMovieSessionResponseLinks(req: ServerRequest, response: List<MovieSessionResponse>): Mono<List<MovieSessionResponse>> {
        return just(response.map { addMovieSessionResponseLinks(req.uri().toString(), it) })
    }

    fun addMovieSessionResponseLinks(req: ServerRequest, response: MovieSessionResponse): Mono<MovieSessionResponse> {
        return just(addMovieSessionResponseLinks(req.uri().toString(), response))
    }

    fun addReviewResponseLinks(req: ServerRequest, response: List<ReviewResponse>): Mono<List<ReviewResponse>> {
        return just(response.map { addReviewResponseLinks(req.uri().toString(), it) })
    }

    fun addReviewResponseLinks(req: ServerRequest, response: ReviewResponse): Mono<ReviewResponse> {
        return just(addReviewResponseLinks(req.uri().toString(), response))
    }

    fun addMovieResponseLinks(req: ServerRequest, response: List<MovieResponse>): Mono<List<MovieResponse>> {
        return just(response.map { addMovieResponseLinks(req.uri().toString(), it) })
    }

    fun addMovieResponseLinks(req: ServerRequest, response: MovieResponse): Mono<MovieResponse> {
        return just(addMovieResponseLinks(req.uri().toString(), response))
    }

    private fun addMovieSessionResponseLinks(uri: String, response: MovieSessionResponse): MovieSessionResponse {
        val links = listOf(
            mapLink(GET, uri.plus("movies/${response.movieId}")),
            mapLink(POST,uri.plus("movies/${response.movieId}/sessions")),
            mapLink(DELETE,uri.plus("movies/${response.movieId}/sessions/${response.id}"))
        )

        return response.copy(links = links)
    }

    private fun addReviewResponseLinks(uri: String, response: ReviewResponse): ReviewResponse {
        val links = listOf(
            mapLink(GET, uri.plus("movies/${response.movieId}")),
            mapLink(GET, uri.plus("movies/${response.movieId}/reviews")),
            mapLink(POST,uri.plus("movies/${response.movieId}/reviews"))
        )

        return response.copy(links = links)
    }

    private fun addMovieResponseLinks(uri: String, response: MovieResponse): MovieResponse {
        val links = listOf(
            mapLink(GET, uri.plus("movies")),
            mapLink(GET, uri.plus("movies/${response.id}")),
            mapLink(GET, uri.plus("movies/${response.id}/sessions")),
            mapLink(POST,uri.plus("movies/${response.id}/sessions")),
            mapLink(GET, uri.plus("movies/${response.id}/reviews")),
            mapLink(POST,uri.plus("movies/${response.id}/reviews")),
        )

        return response.copy(links = links)
    }

    private fun mapLink(method: HttpMethod, href: String) = mapOf("method" to method.name, "href" to href)
}