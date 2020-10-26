package com.example.bible.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.bible.databinding.CellBooksBinding
import com.example.domain.models.bible.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BooksViewHolder>() {

    private val booksList = mutableListOf<Book>()

    fun setAdapterData(newBooksList: List<Book>) {
        booksList.clear()
        booksList.addAll(newBooksList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {

        val cellBook: CellBooksBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_books,
            parent,
            false
        )
        return BooksViewHolder(cellBook)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(booksList[position])
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    class BooksViewHolder(cellBook: CellBooksBinding) : RecyclerView.ViewHolder(cellBook.root) {

        private val bookBinding = cellBook

        fun bind(book: Book) {
            bookBinding.book = book
        }

    }
}