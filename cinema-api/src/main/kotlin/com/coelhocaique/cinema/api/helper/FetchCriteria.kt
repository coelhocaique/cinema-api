package com.coelhocaique.cinema.api.helper

import java.time.LocalDateTime
import java.util.UUID

data class FetchCriteria(
    val id: UUID? = null,
    val movieId: UUID? = null,
    val dateTime: LocalDateTime? = null
) {
    enum class SearchType {
        DATE_TIME
    }

    fun searchType(): SearchType? {

        if (dateTime != null)
            return SearchType.DATE_TIME

        return null
    }
}