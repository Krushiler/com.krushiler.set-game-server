package com.krushiler.util

import kotlin.random.Random

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun generateRandomString(length: Int) = (1..length)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")

fun generateAccessToken() = generateRandomString(16)