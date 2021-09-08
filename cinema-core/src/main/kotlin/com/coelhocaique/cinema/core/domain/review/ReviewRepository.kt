package com.coelhocaique.cinema.core.domain.review

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.util.UUID

interface ReviewRepository: ReactiveMongoRepository<ReviewDocument, UUID> {

    fun findByReferenceIdAndType(referenceId: UUID, type: ReviewType): Flux<ReviewDocument>
}