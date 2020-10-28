package com.example.bible.ui.readbook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bible.App
import com.example.bible.R
import kotlinx.android.synthetic.main.fragment_reading_page.*
import javax.inject.Inject

class ReadingPageFragment : Fragment() {

    @Inject
    lateinit var readingPageFactory: ReadingPageFactory

    lateinit var readingPageViewModel: ReadingPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? AppCompatActivity)?.supportActionBar?.show()
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)

        (activity?.applicationContext as App).bibleComponent.inject(this)

        readingPageViewModel =
            ViewModelProvider(this, readingPageFactory).get(ReadingPageViewModel::class.java)

        setFragmentResultListener("requestKey") { _, bundle ->
            val bookId = bundle.getInt("bundleKey")
            findChapter(bookId)
        }

        setChapter()

    }

    private fun findChapter(bookId: Int) {
        readingPageViewModel.getChapter(bookId)
    }

    private fun setChapter() {
        readingPageViewModel.chapter.observe(viewLifecycleOwner, Observer{

            text_book_chapter.text = it
        })
    }


}