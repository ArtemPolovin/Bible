package com.example.bible.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.bible.utils.READE_BIBLE_TITLE
import com.example.bible.utils.SAVE_PAGE_FALSE
import com.example.bible.utils.SAVE_PAGE_TRUE
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter

    private lateinit var navController: NavController

    @Inject
    lateinit var homeFactory: HomeFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.applicationContext as App).bibleComponent.inject(this)

        homeViewModel = ViewModelProvider(this, homeFactory).get(HomeViewModel::class.java)

        homeAdapter = HomeAdapter()

        rv_home.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = homeAdapter
        }

        navController = Navigation.findNavController(view)

        sendCellDataToAdapter()
        switchFragment()

    }

    private fun switchFragment() {
        homeAdapter.onClickItemListener(object : HomeAdapter.IOnClickHomeItemListener {
            override fun getTitle(title: String) {

                when (title) {
                    READE_BIBLE_TITLE -> {
                        setFragmentResult(
                            "requestKey",
                            bundleOf(
                                "bookId" to homeViewModel.getBookId(),
                                "chapterId" to homeViewModel.getChapterId(),
                                "savePage" to SAVE_PAGE_TRUE
                            )
                        )
                        navController.navigate(R.id.action_nav_home_to_reading_page)
                    }

                }
            }

        })
    }

    private fun sendCellDataToAdapter() {
        homeViewModel.cellHomeList.observe(viewLifecycleOwner, Observer {
            homeAdapter.setupHomeAdapter(it)
        })
    }

}