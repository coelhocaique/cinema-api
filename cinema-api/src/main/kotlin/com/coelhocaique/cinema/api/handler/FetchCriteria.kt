package com.coelhocaique.cinema.api.handler

import java.time.LocalDateTime
import java.util.UUID

data class FetchCriteria(
    val id: UUID? = null,
    val movieId: UUID? = null,
    val sessionDateTimeFrom: LocalDateTime? = null
)