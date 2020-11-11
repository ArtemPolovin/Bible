package com.example.bible.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bible.App
import com.example.bible.R
import com.example.bible.utils.BookViewState
import com.example.bible.utils.Person
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var searchFactory: SearchFactory
    lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (context?.applicationContext as App).bibleComponent.inject(this)

        searchViewModel = ViewModelProvider(this, searchFactory).get(SearchViewModel::class.java)

        searchAdapter = SearchAdapter()

        rv_search_scripture.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_search_scripture.adapter = searchAdapter

        btn_search.setOnClickListener {
            val inputText = edit_search_scripture.text.toString()
            searchViewModel.sendInputText(inputText)
            setUpCellBook()
        }

        setUpCellBook()

        word_seek_bar.setOnSeekBarChangeListener(setSeekBar)
        char_seek_bar.setOnSeekBarChangeListener(setSeekBar)

    }

    private fun setUpCellBook() {
        searchViewModel.bookViewState.observe(viewLifecycleOwner, Observer {

            error_text.visibility = View.GONE
            scroll_view_search.visibility = View.GONE
            rv_search_scripture.visibility = View.GONE
            progress_bar.visibility = View.GONE

            when (it) {
                BookViewState.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
                BookViewState.Error -> {

                }
                is BookViewState.CellBooksLoaded -> {
                    rv_search_scripture.visibility = View.VISIBLE
                    searchAdapter.setUpSearchAdapterData(it.cellBookList)
                    if (it.cellBookList.isEmpty() || edit_search_scripture.text.toString() == "") {
                        error_text.visibility = View.VISIBLE
                        scroll_view_search.visibility = View.VISIBLE
                        rv_search_scripture.visibility = View.GONE
                    }
                }
            }
        })
    }

    private val setSeekBar = object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when (seekBar?.id) {
                R.id.word_seek_bar -> {
                    text_word_seek_bar_percent.text = "$progress%"
                    searchViewModel.setWordMatchPercentage(progress)
                }
                R.id.char_seek_bar ->{
                    text_char_seek_bar_percent.text = "$progress$"
                    searchViewModel.setCharMatchPercentage(progress)
                }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    }

}