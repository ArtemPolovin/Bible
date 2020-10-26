package com.example.bible.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bible.App
import com.example.bible.R
import com.example.bible.utils.BookViewState
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*
import javax.inject.Inject

class BookFragment : Fragment() {

    @Inject
    lateinit var bookFactory: BookFactory
    lateinit var bookViewModel: BookViewModel
    lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (context?.applicationContext as App).bibleComponent.inject(this)

        bookViewModel = ViewModelProvider(this,bookFactory).get(BookViewModel::class.java)

        adapter = BookAdapter()

        rv_books.adapter = adapter
        rv_books.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        setUpBooks()
        refreshingBooksLis()

    }

    fun setUpBooks() {
        bookViewModel.bookViewState.observe(viewLifecycleOwner, Observer {

            pull_refresh_layout.isRefreshing = false
            rv_books.visibility = View.GONE

            when (it) {
                BookViewState.Loading ->{
                    pull_refresh_layout.isRefreshing = true
                }
                is BookViewState.BooksLoaded -> {
                    rv_books.visibility = View.VISIBLE
                   adapter.setAdapterData(it.booksList)
                }
            }
        })
    }

    private fun refreshingBooksLis() {
        pull_refresh_layout.setOnRefreshListener {
            bookViewModel.refreshBooksList()
        }
    }
}