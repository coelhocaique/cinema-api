package com.coelhocaique.cinema.core.domain.session

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

interface MovieSessionRepository: ReactiveMongoRepository<MovieSessionDocument, UUID> {


}