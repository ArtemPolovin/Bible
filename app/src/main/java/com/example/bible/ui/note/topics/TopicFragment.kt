package com.example.bible.ui.note.topics

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.App
import com.example.bible.R
import com.example.bible.ui.viewstate.NoteViewState
import kotlinx.android.synthetic.main.fragment_note_topic.*
import javax.inject.Inject

class TopicFragment : Fragment() {

    @Inject
    lateinit var topicFactory: TopicFactory
    private lateinit var topicViewModel: TopicViewModel

    private lateinit var adapter: TopicAdapter

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setActionBar()

        (activity?.applicationContext as App).bibleComponent.inject(this)

        topicViewModel = ViewModelProvider(this, topicFactory).get(TopicViewModel::class.java)

        adapter = TopicAdapter()

        rv_topics.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_topics.adapter = adapter

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv_topics)

        add_topic_floating_button.setOnClickListener {
            addNewTopicByDialog()
        }

        navController = Navigation.findNavController(view)

        topicViewModel.refreshTopicsList()
        setUpTopicList()
        refreshTopics()
        moveToNotePage()

    }

    private fun setUpTopicList() {
        topicViewModel.topicList.observe(viewLifecycleOwner, Observer {
            when (it) {
                NoteViewState.Loading -> pull_refresh_topics.isRefreshing = true
                NoteViewState.Error -> edit_error.visibility = View.VISIBLE
                is NoteViewState.TopicsLoaded -> {
                    pull_refresh_topics.isRefreshing = false
                    edit_error.visibility = View.GONE
                    adapter.setTopicList(it.topicsList)
                }

            }
        })
    }

    private fun addNewTopicByDialog() {
        val view =
            LayoutInflater.from(requireActivity()).inflate(R.layout.add_new_topic_dialog, null)

        val editNewTopic = view.findViewById<EditText>(R.id.edit_new_topic)

        val builder = AlertDialog.Builder(requireActivity())

        with(builder) {
            setView(view)
            setTitle("New topic")
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            setPositiveButton("Create") { dialog, _ ->
                if (editNewTopic.text.toString().isNotEmpty()) topicViewModel.receiveNewTopic(
                    editNewTopic.text.toString()
                )
                else println("edit = null")
                dialog.dismiss()
            }

            create().show()
        }


    }

    private fun refreshTopics() {
        pull_refresh_topics.setOnRefreshListener {
            topicViewModel.refreshTopicsList()
        }
    }

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterPosition = viewHolder.adapterPosition
                val topicModel = adapter.getItemByAdapterPosition(adapterPosition)
                topicViewModel.receiveRemovedTopic(topicModel.topicId)
            }

        }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.note_topics_tittle)
    }

    private fun moveToNotePage() {
        var bundle: Bundle? = null
        adapter.onClickTopicListener(object : TopicAdapter.OnClickListerTopic {
            override fun getTopicId(topicId: Long) {
                 bundle = bundleOf("topicId" to topicId)
                navController.navigate(R.id.action_topicFragment_to_noteFragment, bundle)
            }

        })
    }

}