package com.example.gitrepos.view.home

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.Data.Items
import com.example.gitrepos.model.Item
import kotlinx.android.synthetic.main.fragment_main_fragment.*
import com.example.gitrepos.model.Data.AppDatabase as AppDatabase

class HomeFragment() : Fragment(), ItemAdapter.OnClickListener, HomeView {

    var dataBase = AppDatabase.getInstance(requireContext())

    override fun clickItem(item: Item) {
        Log.d("click", item.name)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
    }

    lateinit var presenterHome: HomePresenter

    override fun loadList(items: MutableList<Item>) {
        val adapter = ItemAdapter(items, requireContext(),this)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    override fun saveList(items: Items) {

        //val insert = AppDatabase.getInstance(requireContext())
        //Log.d("saveList", insert.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
        presenterHome.getData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_fragment, container, false)
    }

    fun getPresenter(): HomePresenter{
        return HomePresenterImpl(this)
    }

}