package com.example.data.mappers

import com.example.data.utils.MAX_MISMATCHED_WORDS
import com.example.domain.models.CellBook
import com.example.domain.models.bible.Book

class MapFromBookToCellBook {

    fun mapFromBookListToCellBookList(
        bookList: List<Book>,
        inputText: String,
        wordMatchPercentage: Int,
        charMatchPercentage: Int
    ): List<CellBook> {

        val cellBookList = mutableListOf<CellBook>()

        bookList.forEach { book ->
            book.Chapters.forEach { chapter ->
                chapter.Verses.forEach { verse ->

                    val matchedWords = mutableListOf<String>()

                    if (matchVerse(verse.Text, inputText, wordMatchPercentage, charMatchPercentage, matchedWords)) {
                        cellBookList.add(CellBook(book.BookName, chapter.ChapterId, verse.VerseId, verse.Text, matchedWords))
                    }
                }
            }
        }
        return cellBookList
    }

    private fun matchVerse(
        verse: String,
        inputText: String,
        wordMatchPercentage: Int,
        charMatchPercentage: Int,
        matchedWords: MutableList<String>
    ): Boolean {

        var isWordMatched = true
        var isFirstWordMatched = false
        var countOfMatchedWords: Double = 0.0
        var countOfMismatchedWords = 0
        val pattern = """\W+""".toRegex()
        val listOfVerseWords = verse.split(" ").distinct().filter { it.length > 2 }
        val listOfInputWords = pattern.split(inputText).filter { it.isNotBlank() && it.length > 2 }

        firstLoop@ for (verseWord in listOfVerseWords) {

            if (!isWordMatched) {
                countOfMismatchedWords += 1

                if (countOfMismatchedWords > MAX_MISMATCHED_WORDS) break@firstLoop
            }
            secondLoop@ for (inputWord in listOfInputWords) {

                if (compareWords(verseWord, inputWord, charMatchPercentage)) {
                    countOfMatchedWords += 1
                    isFirstWordMatched = true
                    isWordMatched = true

                    matchedWords.add(verseWord)

                    break@secondLoop
                } else if (isFirstWordMatched) {
                    isWordMatched = false
                }
            }

        }
        val percentOfMatchedWords = countOfMatchedWords / listOfInputWords.size * 100

        return percentOfMatchedWords >= wordMatchPercentage
    }

    private fun compareWords(
        textWord: String,
        inputWord: String,
        charMatchPercentage: Int
    ): Boolean {

        val textWordChars = textWord.toCharArray()
        val inputWordChars = inputWord.toCharArray()
        var countOfMatchedChars = 0.0

        loop@ for (i in textWordChars.indices) {
            if (i >= inputWordChars.size) break@loop
            if (textWordChars[i].toLowerCase() == inputWordChars[i].toLowerCase()) {
                countOfMatchedChars += 1
            }

        }
        val percentOfMatchedChars = countOfMatchedChars / inputWordChars.size * 100

        return percentOfMatchedChars >= charMatchPercentage
    }
}