package com.example.bible.ui.books

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.bible.databinding.CellBooksBinding
import com.example.domain.models.bible.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BooksViewHolder>() {

    private val booksList = mutableListOf<Book>()
    private lateinit var onClickListenerBookId: OnClickListenerBookId

    init {
        setHasStableIds(true)
    }

    fun setAdapterData(newBooksList: List<Book>) {
        booksList.clear()
        booksList.addAll(newBooksList)
        notifyDataSetChanged()
    }

    fun onClickItemListener(onClickListenerBookId: OnClickListenerBookId) {
        this.onClickListenerBookId = onClickListenerBookId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {

        val cellBook: CellBooksBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_books,
            parent,
            false
        )
        return BooksViewHolder(cellBook, onClickListenerBookId)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(booksList[position])
        holder.clickItem()
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun getItemId(position: Int): Long {
       return  booksList[position].BookId.toLong()
    }

    class BooksViewHolder(
        cellBook: CellBooksBinding,
        private val onClickListener: OnClickListenerBookId?
    ) : RecyclerView.ViewHolder(cellBook.root) {

        private val bookBinding = cellBook

        fun bind(book: Book) {
            bookBinding.book = book
        }

        fun clickItem() {
            itemView.setOnClickListener {
                onClickListener?.getBookId(itemId.toInt())
            }
        }

    }

    interface OnClickListenerBookId {
        fun getBookId(bookId: Int)
    }
}