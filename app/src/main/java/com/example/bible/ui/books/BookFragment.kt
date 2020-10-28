package com.example.bible.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bible.App
import com.example.bible.R
import com.example.bible.utils.BookViewState
import com.example.bible.utils.MINIMUM_SYMBOLS
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BookFragment : Fragment() {

    @Inject
    lateinit var bookFactory: BookFactory
    lateinit var bookViewModel: BookViewModel
    lateinit var adapter: BookAdapter
    private lateinit var navController: NavController

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        (context?.applicationContext as App).bibleComponent.inject(this)

        navController = Navigation.findNavController(view)

        bookViewModel = ViewModelProvider(this, bookFactory).get(BookViewModel::class.java)

        adapter = BookAdapter()

        rv_books.adapter = adapter
        rv_books.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        setUpBooks()
        sendFilteredBookListToAdapter()
        inputResultToBookSearch()
        refreshingBooksLis()
        goToReadingPage()

    }

    private fun setUpBooks() {
        bookViewModel.bookViewState.observe(viewLifecycleOwner, Observer {

            pull_refresh_layout.isRefreshing = false
            rv_books.visibility = View.GONE

            when (it) {
                BookViewState.Loading -> {
                    pull_refresh_layout.isRefreshing = true
                }
                is BookViewState.BooksLoaded -> {
                    rv_books.visibility = View.VISIBLE
                    adapter.setAdapterData(it.booksList)
                }
            }
        })
    }

    private fun resultFromSearchView(): Flowable<String> {
        return Flowable.create({ emitter ->
            search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    emitter.onNext(query ?: "")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    emitter.onNext(newText ?: "")
                    return false
                }

            })
        }, BackpressureStrategy.LATEST)
    }

    private fun inputResultToBookSearch() {
        disposable = resultFromSearchView()
            .filter { it.length >= MINIMUM_SYMBOLS }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    bookViewModel.bookSearch(it)
                },
                {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }


    private fun sendFilteredBookListToAdapter() {
        bookViewModel.filteredBooks.observe(viewLifecycleOwner, Observer {
            adapter.setAdapterData(it)
        })
    }


    private fun refreshingBooksLis() {
        pull_refresh_layout.setOnRefreshListener {
            bookViewModel.refreshBooksList()
        }
    }

    private fun goToReadingPage() {
        adapter.onClickItemListener(object : BookAdapter.OnClickListenerBookId {
            override fun getBookId(bookId: Int) {

                setFragmentResult("requestKey",bundleOf("bundleKey" to bookId))

                navController.navigate(R.id.action_nav_home_to_readingPageFragment)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}