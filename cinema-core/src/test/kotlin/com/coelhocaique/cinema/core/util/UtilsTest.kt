package com.coelhocaique.cinema.core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UtilsTest {

    @Test
    fun testFormatToUUIDValidFormat() {
        val uuidNoDashes = "0f5a129a8e2e44a3b0ec58974535d849"
        val noDashes = formatToUUID(uuidNoDashes)
        assertEquals("0f5a129a-8e2e-44a3-b0ec-58974535d849", noDashes.toString())

        val uuidWithDashes = "0f5a129a-8e2e-44a3-b0ec-58974535d849"
        val withDashes = formatToUUID(uuidWithDashes)
        assertEquals("0f5a129a-8e2e-44a3-b0ec-58974535d849", withDashes.toString())
    }

    @Test
    fun testFormatToUUIDInvalidFormat() {
        val missingADigit = "0f5a129a8e2e44a3b8974535d849"
        assertThrows(IllegalArgumentException::class.java){
            formatToUUID(missingADigit)
        }

        val invalidUuid = "anything"
        assertThrows(IllegalArgumentException::class.java){
            formatToUUID(invalidUuid)
        }
    }
}