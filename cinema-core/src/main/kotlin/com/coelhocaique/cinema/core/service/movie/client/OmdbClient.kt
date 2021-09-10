package com.coelhocaique.cinema.core.service.movie.client

import com.coelhocaique.cinema.core.config.properties.OmdbApiProperties
import com.coelhocaique.cinema.core.util.logger
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class OmdbClient(private val apiProperties: OmdbApiProperties) {

    @Cacheable("omdbDetails")
    fun retrieveMovieDetails(imdbId: String): Mono<OmdbResponse> {
        val sw = StopWatch()
        sw.start()
        logger().info("M=retrieveMovieDetails, imdbId=$imdbId, stage=init")

        val response = WebClient.create(apiProperties.url!!)
            .get()
            .uri { uriBuilder ->
                uriBuilder.queryParam("apiKey", apiProperties.apiKey!!)
                    .queryParam("i", imdbId)
                    .build()
            }.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()

        sw.stop()
        logger().info("M=retrieveMovieDetails, imdbId=$imdbId, stage=success, time=${sw.lastTaskTimeMillis}")

        return response.bodyToMono()
    }

}