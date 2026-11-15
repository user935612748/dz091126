package com.example.dz091126.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    var isAvailable: Boolean = true
)