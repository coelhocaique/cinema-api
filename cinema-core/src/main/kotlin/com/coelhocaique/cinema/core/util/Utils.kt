package com.coelhocaique.cinema.core.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(javaClass)

fun formatToUUID(uuidString: String): UUID {
    if (!(uuidString.length == 32 || uuidString.length == 36))
        throw IllegalArgumentException()
    val uuid = uuidString.replace("-", "")
    return UUID.fromString(uuid.substring(0, 8)
            .plus("-")
            .plus(uuid.substring(8, 12))
            .plus("-")
            .plus(uuid.substring(12, 16))
            .plus("-")
            .plus(uuid.substring(16, 20))
            .plus("-")
            .plus(uuid.substring(20)))
}