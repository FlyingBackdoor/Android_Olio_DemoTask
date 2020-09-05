package me.sohair.demotask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import me.sohair.demotask.adapters.CategoriesAdapter
import me.sohair.demotask.model.Categories
import me.sohair.demotask.util.MySingleton

class NavigationDrawerActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    //for recyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var itemList: ArrayList<Categories>
    private lateinit var adapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miItem1 -> Toast.makeText(applicationContext,
                    "Clicked on Item 1", Toast.LENGTH_SHORT).show()
                R.id.miItem2 -> Toast.makeText(applicationContext,
                    "Clicked on Item 2", Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(applicationContext,
                    "Clicked on Item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        gridLayoutManager = GridLayoutManager(applicationContext, 3,
            RecyclerView.VERTICAL, false)

        recyclerView.layoutManager = gridLayoutManager

        itemList = ArrayList()

    }

    override fun onStart() {
        super.onStart()

        requestSliders()

        loadCategories()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    private fun requestSliders() {
        val queue = MySingleton.getInstance(this).requestQueue
        val apiURL = "http://jntrcpl.com/Olio_v2.0/Api/get_slider"
        val baseURL = "https://jntrcpl.com/Olio_v2.0/"
        val imageList = ArrayList<SlideModel>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiURL, null,
            { response ->
                //textView.text = "Response: %s".format(response.toString())
                val sliders = response.getJSONArray("data")

                for (i in 0 until sliders.length()) {
                    val item = sliders.getJSONObject(i)
                    //println("Name: " + item.getString("name"))
                    val url = baseURL + item.getString("image")
                    imageList.add(SlideModel(url))
                }

                val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
                imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

                //progressBar3.visibility = View.INVISIBLE

                //Log.d("JSON", response.getJSONArray("data").toString())
            },
            { error ->
                // TODO: Handle error
                Log.d("JSON Slides", "Error: " + error.message)
            }
        )

        queue.add(jsonObjectRequest)


    }

    private fun loadCategories(){
        val queue = MySingleton.getInstance(this).requestQueue
        val apiUrl = "http://jntrcpl.com/Olio_v2.0/Api/get_category"
        val baseURL = "https://jntrcpl.com/Olio_v2.0/"


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response ->

                val categories = response.getJSONArray("data")

                for (i in 0 until categories.length()) {
                    val iObject = categories.getJSONObject(i)
                    val name = iObject.getString("name")
                    val image = iObject.getString("image")

                    itemList.add(Categories(name, baseURL+image))
                }

                adapter = CategoriesAdapter(applicationContext, itemList)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                //Log.d("JSON", response.getJSONArray("data").toString())
            },
            { error ->
                // TODO: Handle error
                Log.d("JSON", "Error: " + error.message)
            }
        )

        queue.add(jsonObjectRequest)
    }
}