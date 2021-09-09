package com.coelhocaique.cinema.core.service.session

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class MovieSessionResponse(
    val id: UUID,
    val movieId: UUID,
    val price: BigDecimal,
    val room: String?,
    val date: LocalDate,
    val time: LocalTime,
    val links: List<Map<String, String>>? = null
)

data class MovieSessionRequest(
    val price: BigDecimal? = null,
    val sessionDateTime: LocalDateTime? = null,
    val room: String? = null
)