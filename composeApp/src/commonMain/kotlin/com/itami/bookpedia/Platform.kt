package com.itami.bookpedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform