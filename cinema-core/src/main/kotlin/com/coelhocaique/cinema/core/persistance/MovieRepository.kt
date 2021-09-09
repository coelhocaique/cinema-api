package com.coelhocaique.cinema.core.persistance

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

interface MovieRepository: ReactiveMongoRepository<MovieDocument, UUID>