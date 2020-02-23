package com.example.gitrepos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.Item

class RepositoryListAdapter (private val list: List<Item>) :
        RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

}

class ItemViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.repository_row,
            parent, false)) {

    private val userText: TextView = itemView.findViewById(R.id.userText)
    private val projectText: TextView = itemView.findViewById(R.id.projectText)
    private val starsText: TextView = itemView.findViewById(R.id.starsText)

    private val image: ImageView = itemView.findViewById(R.id.imageView)

    fun bind (item: Item) {
        userText.text = item.owner.login
        projectText.text = item.name
        starsText.text = "Stars: ${item.stargazersCount}"
        image.setImageBitmap(item.owner.avatar)
    }
}