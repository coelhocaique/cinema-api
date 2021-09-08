package com.coelhocaique.cinema.api.dto

import java.time.LocalDate
import java.util.*

data class ParameterRequestDTO (
        val name: String?,
        val value: String?,
        val referenceDate: LocalDate?,
        val accountId: UUID?
)