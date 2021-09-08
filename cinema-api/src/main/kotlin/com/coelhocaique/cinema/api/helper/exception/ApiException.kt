package com.coelhocaique.cinema.api.helper.exception

data class ApiException(
        val type: ExceptionType,
        val messages: List<String> = listOf(),
        val ex: Throwable? = null
) : RuntimeException(ex) {

    enum class ExceptionType(val status: Int) {
        BUSINESS_EXCEPTION(400),
        UNAUTHORIZED_EXCEPTION(401)
    }

    companion object ApiExceptionHelper {
        fun business(message: String) = ApiException(ExceptionType.BUSINESS_EXCEPTION, listOf(message))

        fun business(message: String, e: Throwable) = ApiException(ExceptionType.BUSINESS_EXCEPTION, listOf(message), e)

        fun unauthorized(message: String) = ApiException(ExceptionType.UNAUTHORIZED_EXCEPTION, listOf(message))
    }
}