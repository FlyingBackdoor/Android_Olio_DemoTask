package me.sohair.demotask.util

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import java.net.URLEncoder

class ApiRequest(context: Context) {

    private var queue = MySingleton.getInstance(context).requestQueue

    private lateinit var categoryImages: MutableMap<String, String>


    fun requestSliders() {
        val url = "http://jntrcpl.com/Olio_v2.0/Api/get_slider"
        val baseURL = "https://jntrcpl.com/Olio_v2.0/"
        val sliderImages =  ArrayList<String>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //textView.text = "Response: %s".format(response.toString())
                val sliders = response.getJSONArray("data")

                for (i in 0 until sliders.length()) {
                    val item = sliders.getJSONObject(i)
                    //println("Name: " + item.getString("name"))
                    val url = baseURL + item.getString("image")
                    sliderImages.add(url)
                }

                Log.d("JSON", response.getJSONArray("data").toString())
            },
            { error ->
                // TODO: Handle error
                Log.d("JSON Slides", "Error: " + error.message)
            }
        )

        queue.add(jsonObjectRequest)


    }

    fun requestCategories(){
        val url = "http://jntrcpl.com/Olio_v2.0/Api/get_category"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val categories = response.getJSONArray("data")

                for (i in 0 until categories.length()) {
                    val item = categories.getJSONObject(i)

                    //println("Name: " + item.getString("name"))

                    categoryImages[item.getString("name")] = item.getString("image")
                }
                Log.d("JSON", response.getJSONArray("data").toString())
            },
            { error ->
                // TODO: Handle error
                Log.d("JSON", "Error: " + error.message)
            }
        )

        queue.add(jsonObjectRequest)
    }



    }