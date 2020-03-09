package com.example.gitrepos.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.item.Item
import com.example.gitrepos.model.item.ItemsDatabase
import com.example.gitrepos.model.profile.Profile
import com.example.gitrepos.model.profile.ProfilesDatabase
import com.example.gitrepos.view.home.adapter.ListItemsAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ListItemsAdapter.OnClickListener, HomeView {

    private lateinit var presenterHome: HomePresenter
    private var adapter: ListItemsAdapter? = null

    override fun clickItem(item: Item) {
        Log.d("click", item.name + item.id)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id!!))
    }

    override fun loadList(items: MutableList<Item>) {

        adapter = ListItemsAdapter(
            items,
            requireContext(),
            this
        )
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_home.setOnRefreshListener {
            Log.d("Swipe", "Updated")
            swipe_home.isRefreshing = false
            presenterHome.getData()
        }

        presenterHome.showData()

        home_toolbar.title = "Repositórios"
        home_toolbar.inflateMenu(R.menu.menu)
        home_toolbar.setOnMenuItemClickListener{
            Log.d("Toolbar", "Click")
            when (it.itemId) {
                R.id.profile -> {
                    if (ProfilesDatabase.getDatabase(requireContext()).profilesDao().getProfile() == null) {
                        val profile = Profile(1, "name", "login", "avatarUrl", null ,"link")
                        ProfilesDatabase.getDatabase(requireContext()).profilesDao().insert(profile)
                    }

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
                val itemsFilter = ItemsDatabase.getDatabase(requireContext())
                    .itemsDao().getFilteredItems("%$newText%")

                Log.d("SearchResult", itemsFilter.toString())
                presenterHome.showFilteredData(itemsFilter)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchSubmit", query!!)
                val itemsFilter = ItemsDatabase.getDatabase(requireContext())
                    .itemsDao().getFilteredItems("%$query%")
                presenterHome.showFilteredData(itemsFilter)
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun getPresenter(): HomePresenter{
        val database = ItemsDatabase.getDatabase(requireContext())
        return HomePresenterImpl(this, database)
    }
}