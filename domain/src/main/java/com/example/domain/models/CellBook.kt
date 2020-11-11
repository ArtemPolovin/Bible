package com.example.domain.models

class CellBook(
    val bookName: String,
    val chapterId: Int,
    val verseId:Int,
    val verse: String,
    val matchedWords: List<String>,
    val bookId : Int
)