package com.coelhocaique.cinema.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.dto.ObjectMapper.toDebtDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.*
import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebt
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebts
import com.coelhocaique.finance.api.helper.Messages.NO_PARAMETERS
import com.coelhocaique.finance.api.helper.RequestValidator.validate
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.domain.dto.DebtResponse
import com.coelhocaique.finance.core.service.DebtService
import com.coelhocaique.cinema.core.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class MovieHandler(private val service: DebtService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return retrieveAccountId(req)
                .flatMap { extractBody<DebtRequestDTO>(req).map { itt -> itt.copy(accountId = it) } }
                .flatMap { validate(it) }
                .flatMap { service.create(toDebtDTO(it)) }
                .flatMap { buildForDebts(req.uri().toString(), it) }
                .let { generateResponse(it, 201) }
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.findById(it.accountId, it.id!!) }
                .flatMap { just(buildForDebt(req.uri().toString(), it)) }
                .let { generateResponse(it) }
    }

    fun fetchDebts(req: ServerRequest): Mono<ServerResponse> {
        return retrieveParameters(req)
                .flatMap {
                    logger().info(it.toString())
                    when (it.searchType()) {
                        REFERENCE_CODE -> findByReferenceCode(it)
                        REFERENCE_DATE -> findByReferenceDate(it)
                        RANGE_DATE -> findByReferenceDateRange(it)
                        else -> error(business(NO_PARAMETERS))
                    }
                }
                .flatMap { buildForDebts(req.uri().toString(), it) }
                .let { generateResponse(it, onEmptyStatus = 204) }
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.deleteById(it.accountId, it.id!!) }
                .let { generateResponse(it, 204) }
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        return retrieveParameters(req)
                .flatMap {
                    logger().info(it.toString())
                    when (it.searchType()) {
                        REFERENCE_CODE -> deleteByReferenceCode(it)
                        else -> error(business(NO_PARAMETERS))
                    }
                }
                .let { generateResponse(it, 204) }
    }

    private fun findByReferenceCode(it: FetchCriteria): Mono<List<DebtResponse>> =
            service.findByReferenceCode(it.accountId, it.referenceCode!!)

    private fun findByReferenceDate(it: FetchCriteria): Mono<List<DebtResponse>> =
            service.findByReferenceDate(it.accountId, it.referenceDate!!)

    private fun findByReferenceDateRange(it: FetchCriteria): Mono<List<DebtResponse>> =
            service.findByReferenceDateRange(it.accountId, it.dateFrom!!, it.dateTo!!)

    private fun deleteByReferenceCode(it: FetchCriteria): Mono<List<DebtResponse>> =
            service.deleteByReferenceCode(it.accountId, it.referenceCode!!)

}