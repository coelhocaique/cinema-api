package com.coelhocaique.cinema.core.domain.review

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document
data class ReviewDocument(
    @Id val id: UUID,
    val type: ReviewType,
    val referenceId: UUID,
    val rating: Double,
    val comment: String?,
    val createdAt: LocalDateTime
)