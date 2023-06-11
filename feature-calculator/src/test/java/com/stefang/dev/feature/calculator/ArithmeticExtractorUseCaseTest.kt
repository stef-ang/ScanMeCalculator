package com.stefang.dev.feature.calculator

import org.junit.Assert.*

import org.junit.Test

class ArithmeticExtractorUseCaseTest {

    @Test
    fun `test extract with plus operator`() {
        val test = ArithmeticExtractorUseCase()

        val expectedList = listOf(
            "10+5" to "15",
            "1+2" to "3",
            "1+2" to "3",
            "1+2" to "3",
            null,
            null,
            null,
            "10.5+3.1" to "13.6"
        )

        listOf(
            "The result of 10 + 5 is",
            "1+2",
            "trijrgtg 1 + 2",
            "1 + 2 wejhvkfe",
            "sdgfsd",
            "10 +",
            "+ 56",
            "10.5 + 3.1"
        ).forEachIndexed { index, input ->
            test.extract(input)?.let { result ->
                assertEquals(expectedList[index]!!.first, result.getInput())
                assertEquals(expectedList[index]!!.second, result.getResult())
            } ?: assertNull(expectedList[index])
        }
    }

    @Test
    fun `test extract with minus operator`() {
        val test = ArithmeticExtractorUseCase()

        val expectedList = listOf(
            "10-6" to "4",
            "1-2" to "-1",
            "1-2" to "-1",
            "1-2" to "-1",
            null,
            null,
            null,
            "10.5-3.1" to "7.4"
        )

        listOf(
            "The result of 10 - 6 is",
            "1-2",
            "trijrgtg 1 - 2",
            "1 - 2 wejhvkfe",
            "sdgfsd",
            "10 -",
            "- 56",
            "10.5 - 3.1"
        ).forEachIndexed { index, input ->
            test.extract(input)?.let { result ->
                assertEquals(expectedList[index]!!.first, result.getInput())
                assertEquals(expectedList[index]!!.second, result.getResult())
            } ?: assertNull(expectedList[index])
        }
    }
}
