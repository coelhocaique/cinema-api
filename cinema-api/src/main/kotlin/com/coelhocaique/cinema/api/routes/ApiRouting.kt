package com.coelhocaique.cinema.api.routes

import com.coelhocaique.cinema.api.handler.MovieHandler
import com.coelhocaique.cinema.api.handler.MovieSessionHandler
import com.coelhocaique.cinema.api.handler.ReviewHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ApiRouting {

    @Bean
    fun movieRoutes(handler: MovieHandler) = router {
        GET("/movies", handler::findAll)
        GET("/movies/{id}", handler::findById)
    }

    @Bean
    fun movieReviewRoutes(handler: ReviewHandler) = router {
        GET("/movies/{id}/reviews", handler::findMovieReviews)
        POST("/movies/{id}/reviews", handler::createMovieReview)
    }

    @Bean
    fun movieSessionRoutes(handler: MovieSessionHandler) = router {
        GET("/movies/{movieId}/sessions", handler::findByMovieId)
        POST("/movies/{movieId}/sessions", handler::create)
        DELETE("/movies/{movieId}/sessions/{id}", handler::delete)
    }
}