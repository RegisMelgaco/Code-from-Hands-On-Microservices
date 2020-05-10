package com.microservices.chapter3

data class SimpleObject(val name: String = "hello") {
    private val _place = "world"
    val place
        get() = _place
}