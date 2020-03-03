package com.example.gitrepos.view.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.CustomTarget
import com.example.gitrepos.R
import com.example.gitrepos.model.Item
import kotlinx.android.synthetic.main.repository_row.view.*
import android.R.attr.fragment
import android.text.method.TextKeyListener.clear
import android.widget.ProgressBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget as ViewTarget1


class ItemAdapter(val list: MutableList<Item>,
                  val context: Context,
                  val listener: OnClickListener
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

        Log.d("On Bind", model.owner.avatarUrl)

        holder.progressBar.visibility = View.INVISIBLE

        val image = Glide.with(context)
            .asBitmap()
            .load(model.owner.avatarUrl)
            .into(holder.image)





        holder.itemView.setOnClickListener {
            Log.d("Holder", "Teste")
            listener.clickItem(model)
            true
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