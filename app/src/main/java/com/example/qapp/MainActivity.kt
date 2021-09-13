package com.example.qapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.qapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent( this, QuestionsActivity::class.java)
            startActivity(intent)
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent( this, AddActivity::class.java)
            startActivity(intent)
        }

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

    }

}