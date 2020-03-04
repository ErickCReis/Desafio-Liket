package com.example.gitrepos.view.home

import android.os.Bundle
import android.util.Log
import android.view.*
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
import kotlinx.android.synthetic.main.fragment_main_fragment.*

class HomeFragment : Fragment(), ItemAdapter.OnClickListener, HomeView {

    lateinit var presenterHome: HomePresenter

    override fun clickItem(item: Item) {
        Log.d("click", item.name)
        val itemId = ItemsDatabase.getDatabase(requireContext()).itemsDao().getItemId(item.name)
        Log.d("click", itemId.toString())
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item, itemId))
    }

    override fun loadList(items: MutableList<Item>) {
        if(items.isEmpty()) {
            Toast.makeText(requireContext(), "Sem dados e conexão", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.INVISIBLE

        } else {
            val adapter = ItemAdapter(items, requireContext(),this)
            recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter

            progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
        presenterHome.getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenterHome.showData()
        home_toolbar.title = "Repositórios"
        home_toolbar.inflateMenu(R.menu.menu)

        home_toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.profile -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                }
            }
            Log.d("Toolbar", "Click")
            true
        }



        val search: SearchView = home_toolbar.findViewById(R.id.search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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