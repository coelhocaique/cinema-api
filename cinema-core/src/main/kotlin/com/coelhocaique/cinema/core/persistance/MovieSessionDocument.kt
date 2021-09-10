package com.coelhocaique.cinema.core.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Document("movieSession")
data class MovieSessionDocument(
    @Id @Field(targetType = FieldType.STRING) val id: UUID,
    @Field(targetType = FieldType.STRING) val movieId: UUID,
    val price: BigDecimal,
    val room: String? = null,
    val date: LocalDate,
    val time: LocalTime,
    val createdAt: LocalDateTime,
    val active: Boolean
)