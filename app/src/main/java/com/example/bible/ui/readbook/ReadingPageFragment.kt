package com.example.bible.ui.readbook

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bible.App
import com.example.bible.R
import kotlinx.android.synthetic.main.fragment_reading_page.*
import javax.inject.Inject

class ReadingPageFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var readingPageFactory: ReadingPageFactory

    lateinit var readingPageViewModel: ReadingPageViewModel

    private lateinit var arrayAdapter: ArrayAdapter<Int>

    private lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? AppCompatActivity)?.setSupportActionBar(custom_toolbar)

        (activity?.applicationContext as App).bibleComponent.inject(this)

        readingPageViewModel =
            ViewModelProvider(this, readingPageFactory).get(ReadingPageViewModel::class.java)

        setFragmentResultListener("requestKey") { _, bundle ->
            val bookId = bundle.getInt("bookId")
            val chapterId = bundle.getInt("chapterId")
            val savePage = bundle.getBoolean("savePage")
            readingPageViewModel.receiveBookData(bookId,chapterId,savePage)
        }

        navController = Navigation.findNavController(view)

        btn_next_chapter.setOnClickListener(this)
        btn_prev_chapter.setOnClickListener(this)

        setChapter()
        showSwitchPageButton()
        setTitle()
        setupSpinner()
        setSpinnerPosition()

    }

    private fun setChapter() {
        readingPageViewModel.chapter.observe(viewLifecycleOwner, Observer {
            text_book_chapter.text = it
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next_chapter -> {
                readingPageViewModel.onClickButtonSwitchPage(true)
                scroll_view.scrollTo(scroll_view.top,0)
            }
            R.id.btn_prev_chapter -> {
                readingPageViewModel.onClickButtonSwitchPage(false)
                scroll_view.scrollTo(scroll_view.top,0)
            }
        }
    }

    private fun showSwitchPageButton() {
        var lastNumberOfChapter = 0
        readingPageViewModel.lastChapterNumber.observe(viewLifecycleOwner, Observer {
            lastNumberOfChapter = it
        })

        readingPageViewModel.numberOfChapter.observe(
            viewLifecycleOwner,
            Observer { numberOfChapter ->
                btn_prev_chapter.visibility = View.GONE

                if (numberOfChapter == lastNumberOfChapter) {
                    btn_next_chapter.visibility = View.GONE
                    btn_prev_chapter.visibility = View.VISIBLE
                } else if (numberOfChapter > 1) {
                    btn_prev_chapter.visibility = View.VISIBLE
                    btn_next_chapter.visibility = View.VISIBLE
                }
            })
    }

    private fun setTitle() {
        readingPageViewModel.bookName.observe(viewLifecycleOwner, Observer {
            title.text = it
            sub_title.text = "глава"
        })
    }

    private fun setupSpinner() {
        readingPageViewModel.chapterNumbers.observe(viewLifecycleOwner, Observer {

            arrayAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            ).also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinner.adapter = arrayAdapter

        })
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item: Int = parent?.getItemAtPosition(position) as Int
        readingPageViewModel.chosenChapter(item)
        scroll_view.scrollTo(scroll_view.top,0)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun setSpinnerPosition() {
        readingPageViewModel.numberOfChapter.observe(viewLifecycleOwner, Observer{
            spinner.setSelection(arrayAdapter.getPosition(it))
        })
    }

}