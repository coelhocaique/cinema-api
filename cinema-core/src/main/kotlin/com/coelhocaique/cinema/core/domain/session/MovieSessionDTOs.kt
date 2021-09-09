package com.coelhocaique.cinema.core.domain.session

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class MovieSessionResponse(
    val id: UUID,
    val movieId: UUID,
    val price: BigDecimal,
    val room: String?,
    val time: LocalDateTime,
    val createdAt: LocalDateTime
)

data class MovieSessionRequest(
    val price: BigDecimal?,
    val time: LocalDateTime?,
    val room: String? = null
)