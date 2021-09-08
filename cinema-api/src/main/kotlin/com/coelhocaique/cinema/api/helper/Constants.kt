package com.coelhocaique.cinema.api.helper

object Messages {
    const val DEFAULT_ERROR_MESSAGE = "Internal error, please try again."
    const val NO_PARAMETERS = "No parameters informed."
    const val MISSING_HEADERS = "Not authorized to perform the request."
    const val NOT_NULL = "%s must not be null."
    const val INVALID_REQUEST = "Invalid request body."
    const val INVALID_ID = "Invalid id."
    const val INVALID_RATING_RANGE = "Rating range must be between 1 and 5."
}

object Fields {
    const val ID = "id"
    const val RATING = "rating"
}