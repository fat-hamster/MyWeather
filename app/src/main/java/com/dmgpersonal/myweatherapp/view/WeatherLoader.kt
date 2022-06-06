package com.dmgpersonal.myweatherapp.view

import android.os.Handler
import android.util.Log
import com.dmgpersonal.myweatherapp.BuildConfig
import com.dmgpersonal.myweatherapp.model.WeatherDTO
import com.dmgpersonal.myweatherapp.model.WeatherDTOOpenWeather
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {

    interface WeatherLoaderListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }

    fun loadWeather() =
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}&lang=ru_RU")
//              URL("https://community-open-weather-map.p.rapidapi.com/forecast/daily?${lat}&lon=${lon}&cnt=10")
            val handler = Handler()

            Thread {
                lateinit var urlConnection: HttpsURLConnection

                try {
                    urlConnection = (uri.openConnection() as HttpsURLConnection).apply {
                        requestMethod = "GET"
                        readTimeout = 10000

                        addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
//                      addRequestProperty("X-RapidAPI-Key", BuildConfig.OPEN_WEATHER_API_KEY)
                    }

                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result = getLines(reader)

                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(result, WeatherDTO::class.java)
//                    val openWeatherDTO: WeatherDTO =
//                        Gson().fromJson(result, WeatherDTOOpenWeather::class.java)

                    handler.post { listener.onLoaded(weatherDTO) }
//                    handler.post { listener.onLoaded(openWeatherDTO) }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    handler.post { listener.onFailed(e) }
                } finally {
                    urlConnection.disconnect()
                }
            }.start()

        } catch (e: MalformedURLException) {
            Log.e("", "URL error")
            e.printStackTrace()
            listener.onFailed(e)
        }

    private fun getLines(reader: BufferedReader): String =
        reader.lines().collect(Collectors.joining("\n"))
}