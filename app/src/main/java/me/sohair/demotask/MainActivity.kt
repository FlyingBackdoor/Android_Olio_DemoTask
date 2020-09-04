package me.sohair.demotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.sohair.demotask.util.ApiRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()


//        apiRequest.requestCategories()

        val intent = Intent(this, NavigationDrawerActivity::class.java)
        startActivity(intent)
        finish()
    }


}