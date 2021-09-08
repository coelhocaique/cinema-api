package com.coelhocaique.cinema.api.routes

import com.coelhocaique.cinema.api.handler.MovieHandler
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
    fun movieSessionRoutes(handler: MovieHandler) = router {
        GET("/movies/{movieId}/sessions", handler::findAll)
        POST("/movies/{movieId}/sessions", handler::findById)
        PATCH("/movies/{movieId}/sessions/{id}", handler::findById)
        DELETE("/movies/{movieId}/sessions/{id}", handler::findById)
    }

    @Bean
    fun reviewRoutes(handler: MovieHandler) = router {
        GET("/reviews", handler::findAll)
        POST("/reviews", handler::findById)
    }

}