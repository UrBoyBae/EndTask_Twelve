package com.example.myweatherapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        supportActionBar?.hide()

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val edCity =  findViewById<EditText>(R.id.city)

        btnSearch.setOnClickListener {
            val kota = edCity.text.toString()

            Intent(this, MainActivity::class.java).also {
                it.putExtra("EXTRA_KOTA",kota)

                startActivity(it)
            }
        }
    }
}