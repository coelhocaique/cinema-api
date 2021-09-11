package com.coelhocaique.cinema.api.helper.exception

import org.springframework.http.HttpStatus

data class ApiException(
        val type: ExceptionType,
        val errorMessage: String? = null,
        val ex: Throwable? = null
) : RuntimeException(ex) {

    enum class ExceptionType(val status: HttpStatus) {
        BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST),
    }

    companion object ApiExceptionHelper {
        fun business(message: String?) = ApiException(ExceptionType.BUSINESS_EXCEPTION, message)

        fun business(message: String?, e: Throwable) = ApiException(ExceptionType.BUSINESS_EXCEPTION, message, e)
    }
}