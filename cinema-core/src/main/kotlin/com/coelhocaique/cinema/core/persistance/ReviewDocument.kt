package com.coelhocaique.cinema.core.persistance

import com.coelhocaique.cinema.core.service.review.ReviewType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("review")
data class ReviewDocument(
    @Id val id: UUID,
    val type: ReviewType,
    val referenceId: UUID,
    val rating: Double,
    val comment: String?,
    val createdAt: LocalDateTime
)