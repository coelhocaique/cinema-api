package com.coelhocaique.cinema.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("omdbapi")
data class OmdbApiProperties(var url: String? = null, var apiKey: String? = null)