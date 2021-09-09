package com.coelhocaique.cinema.core.domain.session

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Document
data class MovieSessionDocument(
    @Id val id: UUID,
    val movieId: UUID,
    val price: BigDecimal,
    val room: String? = null,
    val time: LocalDateTime,
    val createdAt: LocalDateTime,
    val active: Boolean
)