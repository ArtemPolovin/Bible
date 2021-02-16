package com.example.bible.ui.note.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.domain.models.TopicModel

class TopicAdapter : RecyclerView.Adapter<TopicAdapter.TopicHolder>() {

    private val topicList = mutableListOf<TopicModel>()

    private lateinit var onClickListerTopic: OnClickListerTopic

    init {
        setHasStableIds(true)
    }

    fun setTopicList(newList: List<TopicModel>) {
        topicList.clear()
        topicList.addAll(newList)
        notifyDataSetChanged()
    }

    fun onClickTopicListener(onClickListerTopic: OnClickListerTopic) {
        this.onClickListerTopic = onClickListerTopic
    }

    fun getItemByAdapterPosition(position: Int) = topicList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_topics, parent, false)
        return TopicHolder(view, onClickListerTopic)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        holder.bind(topicList[position])
        holder.onClick()
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun getItemId(position: Int): Long {
        return topicList[position].topicId
    }

    class TopicHolder(itemView: View, private val onClickListerTopic: OnClickListerTopic?) :
        RecyclerView.ViewHolder(itemView) {

        private val editTopic = itemView.findViewById<TextView>(R.id.cell_text_topic)

        fun bind(topic: TopicModel) {
            editTopic.text = topic.topic
        }

        fun onClick() {
            itemView.setOnClickListener {
                onClickListerTopic?.getTopicId(itemId)
            }
        }

    }

    interface OnClickListerTopic {
        fun getTopicId(topicId: Long)
    }

}