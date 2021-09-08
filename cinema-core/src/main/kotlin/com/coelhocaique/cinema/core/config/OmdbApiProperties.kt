package com.coelhocaique.cinema.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("omdbapi")
data class OmdbApiProperties(val url: String?, val apiKey: String?)