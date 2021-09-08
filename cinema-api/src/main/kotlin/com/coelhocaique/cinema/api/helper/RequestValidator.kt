package com.coelhocaique.cinema.api.helper

import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

object RequestValidator {

//    fun validate(dto: DebtRequestDTO): Mono<DebtRequestDTO> {
//        return try {
//            nonNull(dto.amount, AMOUNT)
//            nonNull(dto.debtDate, DEBT_DATE)
//            nonNull(dto.description, DESCRIPTION)
//            nonNull(dto.installments, INSTALLMENTS)
//            nonNull(dto.tag, TAG)
//            nonNull(dto.type, TYPE)
//            just(dto)
//        } catch (e: IllegalArgumentException){
//            error(business(e.message!!))
//        }
//    }
//
//    fun validate(dto: IncomeRequestDTO): Mono<IncomeRequestDTO> {
//        return try {
//            nonNull(dto.grossAmount, GROSS_AMOUNT)
//            nonNull(dto.receiptDate, RECEIPT_DATE)
//            nonNull(dto.description, DESCRIPTION)
//            nonNull(dto.referenceDate, REF_DATE)
//            nonNull(dto.sourceName, SOURCE_NAME)
//            dto.discounts.forEach {
//                nonNull(it.amount, AMOUNT)
//                nonNull(it.description, DESCRIPTION)
//            }
//            dto.additions.forEach {
//                nonNull(it.amount, AMOUNT)
//                nonNull(it.description, DESCRIPTION)
//            }
//            just(dto)
//        } catch (e: IllegalArgumentException){
//            error(business(e.message!!))
//        }
//    }
//
//    fun validate(dto: ParameterRequestDTO): Mono<ParameterRequestDTO> {
//        return try {
//            nonNull(dto.name, NAME)
//            nonNull(dto.value, VALUE)
//            nonNull(dto.referenceDate, REF_DATE)
//            just(dto)
//        } catch (e: IllegalArgumentException){
//            error(business(e.message!!))
//        }
//    }
//
//    fun validate(dto: CustomAttributeRequestDTO): Mono<CustomAttributeRequestDTO> {
//        return try {
//            nonNull(dto.propertyName, PROPERTY_NAME)
//            nonNull(dto.value, VALUE)
//            just(dto)
//        } catch (e: IllegalArgumentException){
//            error(business(e.message!!))
//        }
//    }
//
//    fun validate(dto: RecurringDebtRequestDTO): Mono<RecurringDebtRequestDTO> {
//        return try {
//            nonNull(dto.amount, AMOUNT)
//            nonNull(dto.description, DESCRIPTION)
//            nonNull(dto.tag, TAG)
//            nonNull(dto.type, TYPE)
//            just(dto)
//        } catch (e: IllegalArgumentException){
//            error(business(e.message!!))
//        }
//    }
//
//    private fun nonNull(o: Any?, attr: String) = requireNotNull(o, { NOT_NULL.format(attr)})

}