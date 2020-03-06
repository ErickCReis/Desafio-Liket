package com.example.gitrepos.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.data.Item
import com.example.gitrepos.model.data.ItemsDatabase
import com.example.gitrepos.presenter.ItemPresenter
import kotlinx.android.synthetic.main.fragment_main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ItemAdapter.OnClickListener, HomeView {

    private val itemPresenter: ItemPresenter by viewModel()

    private lateinit var presenterHome: HomePresenter
    private var adapter: ItemAdapter? = null

    override fun clickItem(item: Item) {
        Log.d("click", item.name)
        val itemId = ItemsDatabase.getDatabase(requireContext()).itemsDao().getItemId(item.name)
        Log.d("click", itemId.toString())
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item, itemId))
    }

    override fun loadList(items: MutableList<Item>) {
        if(items.isEmpty()) {
            Toast.makeText(requireContext(), "Sem dados", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.INVISIBLE

        } else {
            adapter = ItemAdapter(items, requireContext(),this)
            recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
        //presenterHome.setView(itemPresenter, this)
        //presenterHome.getData()
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            Log.d("Swipe", "Updated")
            swipe.isRefreshing = false
            presenterHome.getData()
        }

        presenterHome.showData()

        home_toolbar.title = "Repositórios"
        home_toolbar.inflateMenu(R.menu.menu)
        home_toolbar.setOnMenuItemClickListener{
            Log.d("Toolbar", "Click")
            when (it.itemId) {
                R.id.profile -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                }
            }
            Log.d("Toolbar", "Click")
            true
        }

        val search = home_toolbar.findViewById(R.id.search) as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Search", newText!!)
                val search = "%$newText%"
                val itemsFilter = ItemsDatabase.getDatabase(requireContext())
                                                .itemsDao().getFilteredItems(search)

                Log.d("SearchResult", itemsFilter.toString())
                presenterHome.showFilteredData(itemsFilter)
//                adapter!!.filter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchSubmit", query!!)
                val itemsFilter = ItemsDatabase.getDatabase(requireContext())
                    .itemsDao().getFilteredItems(query)
                presenterHome.showFilteredData(itemsFilter)
//                adapter!!.filter.filter(query)
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_fragment, container, false)
    }

    private fun getPresenter(): HomePresenter{
        val database = ItemsDatabase.getDatabase(requireContext())
        return HomePresenterImpl(this, database)
    }

}