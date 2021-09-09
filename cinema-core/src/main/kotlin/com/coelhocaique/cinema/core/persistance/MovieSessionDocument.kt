package com.coelhocaique.cinema.core.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Document
data class MovieSessionDocument(
    @Id val id: UUID,
    val movieId: UUID,
    val price: BigDecimal,
    val room: String? = null,
    val date: LocalDate,
    val time: LocalTime,
    val createdAt: LocalDateTime,
    val active: Boolean
)