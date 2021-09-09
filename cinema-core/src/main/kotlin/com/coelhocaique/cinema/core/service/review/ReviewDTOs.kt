package com.coelhocaique.cinema.core.service.review

import java.time.LocalDateTime
import java.util.UUID

data class ReviewResponse(
    val id: UUID,
    val movieId: UUID,
    val rating: Double,
    val comment: String?,
    val createdAt: LocalDateTime,
    val links: List<Map<String, String>>? = null
)

data class ReviewRequest(
    val rating: Double? = null,
    val comment: String? = null
)
