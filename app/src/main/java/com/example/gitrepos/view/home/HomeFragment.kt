package com.example.gitrepos.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.data.Item
import com.example.gitrepos.model.data.ItemsDatabase
import kotlinx.android.synthetic.main.fragment_main_fragment.*

class HomeFragment : Fragment(), ItemAdapter.OnClickListener, HomeView {

    lateinit var presenterHome: HomePresenter

    override fun clickItem(item: Item) {
        Log.d("click", item.name)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
    }

    override fun loadList(items: MutableList<Item>) {
        val adapter = ItemAdapter(items, requireContext(),this)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
        presenterHome.getData()

        Log.d("Home", ItemsDatabase.getDatabase(context!!).itemsDao().getAllItems().toString())

//        val test = Items(null, "test", 1)
//        dataBase.itemsDao().insert(test)
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

    @SuppressLint("WrongThread")
    override fun onDestroy() {
        super.onDestroy()
        ItemsDatabase.getDatabase(requireContext()).clearAllTables()
    }

    private fun getPresenter(): HomePresenter{
        val database = ItemsDatabase.getDatabase(requireContext()).itemsDao()
        return HomePresenterImpl(this, database)
    }

}