package com.coelhocaique.cinema.api.helper

object Messages {
    const val DEFAULT_ERROR_MESSAGE = "Internal error, please try again."
    const val NOT_NULL = "%s must not be null."
    const val INVALID_REQUEST = "Invalid request body."
    const val INVALID_PATH_PARAM = "Invalid path param %s."
    const val INVALID_RATING_RANGE = "Rating range must be between 1 and 5."
}

object Fields {
    const val ID = "id"
    const val MOVIE_ID = "movieId"
    const val RATING = "rating"
    const val PRICE = "price"
    const val SESSION_DATETIME = "sessionDateTime"
    const val DATE_TIME = "dateTime"
}