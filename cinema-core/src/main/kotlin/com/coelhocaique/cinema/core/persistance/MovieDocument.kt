package com.coelhocaique.cinema.core.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document
data class MovieDocument(
    @Id val id: UUID,
    val imdbId: String,
    val title: String,
    val createdAt: LocalDateTime
)