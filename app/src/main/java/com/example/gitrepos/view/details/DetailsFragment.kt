package com.example.gitrepos.view.details

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gitrepos.R
import com.example.gitrepos.model.item.Item
import com.example.gitrepos.model.item.ItemsDatabase
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(), DetailsView {

    private lateinit var presenterDetails: DetailsPresenter
    private var itemId: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemId = DetailsFragmentArgs.fromBundle(arguments!!).itemId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.tool_detalis)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        button.setOnClickListener {
            val url = ItemsDatabase.getDatabase(requireContext()).itemsDao().getItem(itemId).link
            val link = Intent(ACTION_VIEW, Uri.parse(url))
            startActivity(link)
        }

        update_button.setOnClickListener {
            val nome = details_name_edit.text.toString()
            Log.d("UpdateButton", nome)

            if(nome != "") {
                val item = ItemsDatabase.getDatabase(requireContext()).itemsDao().getItem(itemId)
                item.name = nome
                ItemsDatabase.getDatabase(requireContext()).itemsDao().update(item)
                activity!!.onBackPressed()
            }
        }

        photo.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToMyDialogFragment())
        }

        presenterDetails = getPresenter()
        presenterDetails.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun loadDetails(item: Item) {
        details_name.text = item.name
        details_user.text = item.owner.login
        details_stars.text = item.stargazersCount.toString()

        Glide.with(requireContext())
            .asBitmap()
            .load(item.owner.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_radio_button)
            .error(R.drawable.ic_error_black)
            .into(details_image)
    }

    private fun getPresenter(): DetailsPresenter {
        val database = ItemsDatabase.getDatabase(requireContext())
        return DetailsPresenterImpl(this, itemId, database)
    }
}