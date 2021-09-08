package com.coelhocaique.cinema.core.domain.session

import org.springframework.stereotype.Service

@Service
class MovieSessionService(private val repository: MovieSessionRepository)