package com.dmgpersonal.myweatherapp.model

interface Repository {
    fun getWeatherFromLocalStorageRus() : List<Weather>
    fun getWeatherFromLocalStorageWorld() : List<Weather>
    fun getWeatherFromServer() : Weather
}

class RepositoryImpl : Repository {
    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

}