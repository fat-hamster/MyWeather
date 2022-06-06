package com.dmgpersonal.myweatherapp.model

import kotlin.collections.List

interface Repository {
    fun getWeatherFromLocalStorageRus() : List<Weather>
    fun getWeatherFromLocalStorageWorld() : List<Weather>
    fun getWeatherFromServer() : Weather
}

class RepositoryImpl : Repository {
    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()

    override fun getWeatherFromServer(): Weather = Weather()
}