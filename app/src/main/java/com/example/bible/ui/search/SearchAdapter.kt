package com.example.bible.ui.search

import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.bible.databinding.CellSearchPlaceOfScriptureBinding
import com.example.domain.models.CellBook

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {

    private val cellBookList = mutableListOf<CellBook>()
    private lateinit var onCellBookClickListener: OnCellBookClickListener

    fun onClickItemListener(onCellBookClickListener: OnCellBookClickListener) {
        this.onCellBookClickListener = onCellBookClickListener
    }

    fun setUpSearchAdapterData(newList: List<CellBook>) {
        cellBookList.clear()
        cellBookList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val cellBook: CellSearchPlaceOfScriptureBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_search_place_of_scripture,
            parent,
            false
        )
        return SearchAdapterViewHolder(cellBook, onCellBookClickListener)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        val cellBook = cellBookList[position]
        holder.bind(cellBook)
        holder.onClick(cellBook)
    }

    override fun getItemCount(): Int {
        return cellBookList.size
    }

    class SearchAdapterViewHolder(
        cellBook: CellSearchPlaceOfScriptureBinding,
        private val cellBookListener: OnCellBookClickListener?
    ) :
        RecyclerView.ViewHolder(cellBook.root) {

        private val bookBinding = cellBook

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(cellBook: CellBook) {
            bookBinding.book = cellBook

            val originalTExt = cellBook.verse
            val spanString = SpannableString(originalTExt)

            cellBook.matchedWords.forEach {

                val bcsYellow = BackgroundColorSpan(Color.YELLOW)

                val start = originalTExt.indexOf(it, ignoreCase = true)
                val end = it.length + start

                spanString.setSpan(bcsYellow, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            }
            bookBinding.verseText.text = spanString

        }

        fun onClick(cellBook: CellBook) {
            itemView.setOnClickListener {
                cellBookListener?.getCellBook(cellBook)
            }

        }

    }

    interface OnCellBookClickListener {
        fun getCellBook(cellBook: CellBook)
    }
}