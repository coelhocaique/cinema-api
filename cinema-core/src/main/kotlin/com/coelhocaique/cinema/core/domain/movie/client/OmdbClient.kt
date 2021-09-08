package com.coelhocaique.cinema.core.domain.movie.client

import com.coelhocaique.cinema.core.config.OmdbApiProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class OmdbClient(private val apiProperties: OmdbApiProperties) {

    fun retrieveMovieDetails(imdbId: String): Mono<OmdbResponse> {
        val client: WebClient = WebClient.builder()
            .baseUrl(apiProperties.url!!)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(mapOf("apiKey" to apiProperties.apiKey!!, "i" to imdbId))
            .build()

        return client.get().retrieve().bodyToMono()
    }

}