package com.example.catphotoupload.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.catphotoupload.R
import com.example.catphotoupload.fragment.AddCatFragment
import com.example.catphotoupload.fragment.AllCatsFragment
import com.example.catphotoupload.fragment.MyCatsFragment
import com.example.catphotoupload.networking.RetrofitHttp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.*
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add)
        val llAllCats = findViewById<LinearLayout>(R.id.ll_all_cats)
        val llMyCats = findViewById<LinearLayout>(R.id.ll_my_cats)

        replaceFragment(AddCatFragment.newInstance())

        llAllCats.setOnClickListener {
            replaceFragment(AllCatsFragment.newInstance())
        }

        llMyCats.setOnClickListener {
            replaceFragment(MyCatsFragment.newInstance())
        }

        btnAdd.setOnClickListener {
            replaceFragment(AddCatFragment.newInstance())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }

}