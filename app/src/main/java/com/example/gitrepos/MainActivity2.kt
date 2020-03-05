package com.example.gitrepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.facebook.stetho.Stetho

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Stetho.initializeWithDefaults(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fragment).navigateUp()
}
