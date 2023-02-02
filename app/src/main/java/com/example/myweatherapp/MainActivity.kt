package com.example.myweatherapp

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

import android.widget.TextView


class MainActivity : AppCompatActivity() {

    var KOTA: String = "Cimahi"
    val API: String = "a14277e879f0ed96c7ac20986ad76a1f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val kota = intent.getStringExtra("EXTRA_KOTA")

        val location = kota

        KOTA = location.toString()

        weatherTask().execute()

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<LinearLayout>(R.id.errortext).visibility = View.GONE

        }

        override fun doInBackground(vararg p0: String?): String? {

            var response: String?

            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$KOTA&appid=$API&units=metric&lang=id")
                        .readText(Charsets.UTF_8)
            } catch (e: Exception) {
                response = null
            }
            return response

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val clouds = jsonObj.getJSONObject("clouds")
                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText = SimpleDateFormat(
                    "EEE, d MMM yyyy",
                    Locale.ENGLISH
                ).format(Date(updatedAt * 1000))
                val temp = main.getString("temp").substring(0, 2)
                val tempMin = "Min Temp : " + main.getString("temp_min").substring(0, 2) + "°C"
                val tempMax = "Max Temp : " + main.getString("temp_max").substring(0, 2) + "°C"
                val cloudsAll = "Kondisi Awan : " + clouds.getString("all") + "%"
                val pressure = main.getString("pressure") + " hPa"
                val humidity = main.getString("humidity") + "%"
                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed") + " m/s"
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name")
                val country = sys.getString("country")
                val id = weather.getInt("id")
                val icon = weather.getString("icon")

                // kondisi untuk icon agar sesuai dengan cuaca dari API
                if (icon == "01d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.sun)
                } else if (icon == "01n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.moon)
                } else if (icon == "02d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.suncloud)
                } else if (icon == "02n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.mooncloud)
                } else if (icon == "03d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.cloud2)
                } else if (icon == "03n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.cloud2)
                } else if (icon == "04d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.broken)
                } else if (icon == "04n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.broken)
                } else if (icon == "09d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.shower)
                } else if (icon == "09n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.shower)
                } else if (icon == "10d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.sunrain)
                } else if (icon == "10n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.moonrain)
                } else if (icon == "11d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.thunderstorm)
                } else if (icon == "11n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.thunderstorm)
                } else if (icon == "13d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.snow2)
                } else if (icon == "13n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.snow2)
                } else if (icon == "50d") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.mist)
                } else if (icon == "50n") {
                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.mist)
                }


//                if (id == 800) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.clear)
//                } else if (id >= 200 && id <= 232) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.storm)
//                } else if (id >= 600 && id <= 622) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.snow)
//                } else if (id >= 701 && id <= 781) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.haze)
//                } else if (id >= 801 && id <= 804) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.cloud)
//                } else if ((id >= 300 && id <= 321) || (id >= 500 && id <= 531)) {
//                    findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.rain)
//                }

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.awan).text = cloudsAll
                findViewById<TextView>(R.id.terbit).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                findViewById<TextView>(R.id.terbenam).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                findViewById<TextView>(R.id.angin).text = windSpeed
                findViewById<TextView>(R.id.tekanan).text = pressure
                findViewById<TextView>(R.id.kelembaban).text = humidity
                findViewById<TextView>(R.id.negara).text = country

                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE


            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<LinearLayout>(R.id.errortext).visibility = View.VISIBLE
            }
        }

    }
}