package com.example.bible.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.bible.databinding.CellHomeBinding
import com.example.domain.models.CellHome

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val cellHomeList = mutableListOf<CellHome>()

    private lateinit var onClickHomeItemListener: IOnClickHomeItemListener

    fun setupHomeAdapter(newCellHomeList: List<CellHome>) {
        cellHomeList.clear()
        cellHomeList.addAll(newCellHomeList)
        notifyDataSetChanged()
    }

    fun onClickItemListener(onClickHomeItemListener: IOnClickHomeItemListener) {
        this.onClickHomeItemListener = onClickHomeItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val cellHomeBinding: CellHomeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_home,
            parent,
            false
        )
        return HomeViewHolder(cellHomeBinding, parent.context, onClickHomeItemListener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val cellHome = cellHomeList[position]
        holder.bind(cellHome)
        holder.onClickItem(cellHome.title)
    }

    override fun getItemCount(): Int {
        return cellHomeList.size
    }

    class HomeViewHolder(
        cellHome: CellHomeBinding,
        private val context: Context,
        private val onClickHomeItemListener: IOnClickHomeItemListener
    ) :
        RecyclerView.ViewHolder(cellHome.root) {

        private val cellHomeBinding = cellHome
        private val linearLayout =
            itemView.findViewById<LinearLayout>(R.id.cell_linearlalyout_background)

        fun bind(cellHome: CellHome) {
            cellHomeBinding.cellHome = cellHome
            setBackground(cellHome.imagePath)
        }

        fun onClickItem(title: String) {
            itemView.setOnClickListener {
                onClickHomeItemListener.getTitle(title)
            }
        }

        private fun setBackground(drawablePath: String) {
            val imageResource =
                context.resources.getIdentifier(drawablePath, null, context.packageName)
            val image =
                ResourcesCompat.getDrawable(context.resources, imageResource, null)
            linearLayout.background = image

        }


    }

    interface IOnClickHomeItemListener {
        fun getTitle(title: String)
    }
}