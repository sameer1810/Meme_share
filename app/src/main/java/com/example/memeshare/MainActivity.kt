package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    var imageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"



// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url , null,
                Response.Listener { response ->
                    imageurl = response.getString("url")
                    val imageView: ImageView = findViewById(R.id.imageView)
                    Glide.with(this).load(imageurl).into(imageView)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                })


 // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, check out this cool meme from Reddit $imageurl")
        intent.type = "text/plain"
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()

    }
}