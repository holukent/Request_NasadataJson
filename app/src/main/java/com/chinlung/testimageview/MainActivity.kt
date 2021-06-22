package com.chinlung.testimageview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    var bmp: Bitmap? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        val str = "https://apod.nasa.gov/apod/image/0612/flow_mgs_big.jpg"
//        val url = URL(str)
//        val conn = url.openConnection() as HttpURLConnection


//        lifecycleScope.launch {
//
//            setimage(imageview, getbmp(str))
//        }
    }

    fun ImageView.setimage(bmp: Bitmap?) {
        Log.d("bmp", bmp.toString())
        this.setImageBitmap(null)
        this.setImageBitmap(bmp)
    }

    fun getbmp(str: String): Bitmap? {
        var bmp: Bitmap? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL(str)
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            bmp = BitmapFactory.decodeStream(conn.inputStream)
        }

        return bmp
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}