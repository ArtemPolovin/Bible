package com.example.bible.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bible.App
import com.example.bible.R
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    private  lateinit var homeAdapter: HomeAdapter

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
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = homeAdapter
        }

        sendCellDataToAdapter()

    }

    private fun sendCellDataToAdapter() {
        homeViewModel.cellHomeList.observe(viewLifecycleOwner, Observer{
            homeAdapter.setupHomeAdapter(it)
        })
    }

}