package com.example.gitrepos.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gitrepos.R
import com.example.gitrepos.model.data.Item
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        // This callback will only be called when MyFragment is at least Started.
//        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
//            override fun handleOnBackPressed() {
//                // Handle the back button event
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.tool_detalis)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        val item = DetailsFragmentArgs.fromBundle(arguments!!).item
        Log.d("Details", item.name)

        details_name.text = item.name
        details_user.text = item.owner.login
        details_stars.text = item.stargazersCount.toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

}