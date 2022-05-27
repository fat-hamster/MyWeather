package com.dmgpersonal.myweatherapp.viewmodel

import com.dmgpersonal.myweatherapp.model.Weather

sealed class AppState {
    data class Success(val weatherData : List<Weather>) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
