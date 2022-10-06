package com.tanvi.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

 class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null
    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView
    lateinit var btnNextMeme: Button
    lateinit var btnShareMeme: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNextMeme =findViewById(R.id.btnNextMeme)
        btnShareMeme =findViewById(R.id.btnShareMeme)
        imageView = findViewById(R.id.memeImageView)
        progressBar=findViewById(R.id.progressBar)
        loadMeme()
    }
    fun initializeView(){
    }
    private fun loadMeme() {
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url =  "https://meme-api.herokuapp.com/gimme"

        val  jsonObjectRequest =  JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility =View.GONE
                        return false

                    }

                }).into(imageView)
            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

/*
    private fun loadMeme() {
        val queue = Volley.newRequestQueue( this)
        val url = "https://www.google.com"
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
              //  tex.text = "Response is: ${response.substring(0, 500)}"
            },
          //  Response.ErrorListener { textView.text = "That didn't work!" })
        queue.add(stringRequest)
    }
    */

    fun shareMeme(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this cool meme I gotfree from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View){

        loadMeme()

    }
}


