package com.example.gitrepos

import android.os.Bundle
import android.util.JsonToken
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/search/repositories?q=language:swift&sort=stars"

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            url,
            null,
            Response.Listener<JSONObject> { response ->
                textView.text = "Request Complete!"

                val items = response.getJSONArray("items")
                for (i in 0 until items.length()) {
                    val item = JSONObject(items.getString(i))
                    val owner = item.getJSONObject("owner")

                    val name = item.get("name")
                    val stars = item.get("stargazers_count")
                    val login = owner.get("login")
                    val avatar = owner.get("avatar_url")

                    println("Nome: $name \n" +
                            "Stars: $stars \n" +
                            "Logim: $login \n" +
                            "Avatar: $avatar")
                }
            },
            Response.ErrorListener { textView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}

