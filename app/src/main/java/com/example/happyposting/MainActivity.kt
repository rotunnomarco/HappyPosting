package com.example.happyposting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.happyposting.ui.posts.PostFragment

//var postFragment= PostFragment()

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentLayout, PostFragment())
            commit()
        }
    }
}