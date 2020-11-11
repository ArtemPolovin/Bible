package com.example.bible.ui.search

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.core.util.rangeTo
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.bible.databinding.CellSearchPlaceOfScriptureBinding
import com.example.domain.models.CellBook
import kotlinx.android.synthetic.main.cell_search_place_of_scripture.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {

    private val cellBookList = mutableListOf<CellBook>()

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
        return SearchAdapterViewHolder(cellBook)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        holder.bind(cellBookList[position])
    }

    override fun getItemCount(): Int {
        return cellBookList.size
    }

    class SearchAdapterViewHolder(cellBook: CellSearchPlaceOfScriptureBinding) :
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

          /*  val replaceWith = "<span style='background-color:yellow'> $inputText </span>"
            val originalTExt = cellBook.verse
            val modifiedString = originalTExt.replace(inputText, replaceWith,true)

            bookBinding.verseText.text = Html.fromHtml(modifiedString, HtmlCompat.FROM_HTML_MODE_LEGACY)*/

        }

    }
}