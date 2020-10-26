package com.example.domain.models.bible

data class Book(
    val BookId: Int,
    val BookName: String,
    val Chapters: List<Chapter>
)