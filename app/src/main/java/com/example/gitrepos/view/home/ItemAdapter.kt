package com.example.gitrepos.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitrepos.R
import com.example.gitrepos.model.item.Item
import kotlinx.android.synthetic.main.repository_row.view.*

class ItemAdapter(private var list: MutableList<Item>,
                  private val context: Context,
                  private val listener: OnClickListener
): RecyclerView.Adapter<ItemAdapter.ViewHolder>(), Filterable {

    var filterList = list

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
            .circleCrop()
            .placeholder(R.drawable.ic_radio_button)
            .error(R.drawable.ic_error_black)
            .into(holder.image)

        holder.itemView.setOnClickListener {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                Log.d("Filter", "Filter")
                list = filterResults.values as MutableList<Item>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    filterList
                else
                    filterList.filter { it.name.toLowerCase().contains(queryString) ||
                            it.owner.login.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}