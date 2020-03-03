package com.example.gitrepos.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitrepos.R
import com.example.gitrepos.model.data.Item
import kotlinx.android.synthetic.main.repository_row.view.*
import android.widget.ProgressBar


class ItemAdapter(private val list: MutableList<Item>,
                  private val context: Context,
                  private val listener: OnClickListener
): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.userText.text = model.owner.login
        holder.projectText.text = model.name
        holder.starsText.text = model.stargazersCount.toString()
        holder.progressBar.visibility = View.INVISIBLE

        Glide.with(context)
            .asBitmap()
            .load(model.owner.avatarUrl)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            Log.d("Holder", "Teste")
            listener.clickItem(model)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val userText: TextView = itemView.userText
        val projectText: TextView = itemView.projectText
        val starsText: TextView = itemView.starsText

        val image: ImageView = itemView.imageView

        val progressBar: ProgressBar = itemView.imageProgressBar

    }

    interface OnClickListener {
        fun clickItem(item: Item)
    }
}