package com.example.gitrepos.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.gitrepos.R

class SearchActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private var langSelect: String = "swift"
    private var sortType: String = "stars"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        val langSpinner: Spinner = findViewById(R.id.language_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            langSpinner.adapter = adapter
        }

        val sortSpinner: Spinner = findViewById(R.id.sort_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.sortTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortSpinner.adapter = adapter
        }

        langSpinner.onItemSelectedListener = this
        sortSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

        if (parent.id == R.id.language_spinner){
            langSelect = parent.getItemAtPosition(pos).toString()
        } else {
            sortType = parent.getItemAtPosition(pos).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    fun searchRepositories(view: View) {
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putStringArrayListExtra("dados", arrayListOf(langSelect, sortType))
        }
        startActivity(intent)
    }
}