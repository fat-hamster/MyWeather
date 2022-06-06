package com.dmgpersonal.myweatherapp.model

data class WeatherDTOOpenWeather (

    var city    : OWCity?           = OWCity(),
    var cod     : String?         = null,
    var message : Double?         = null,
    var cnt     : Int?            = null,
    var list    : ArrayList<List<Any?>> = arrayListOf()
)

data class Coord (

    var lon : Double? = null,
    var lat : Double? = null
)

data class OWCity (

    var id         : Int?    = null,
    var name       : String? = null,
    var coord      : Coord?  = Coord(),
    var country    : String? = null,
    var population : Int?    = null,
    var timezone   : Int?    = null
)

data class Temp (

    var day   : Double? = null,
    var min   : Double? = null,
    var max   : Double? = null,
    var night : Double? = null,
    var eve   : Double? = null,
    var morn  : Double? = null
)

data class FeelsLike (

    var day   : Double? = null,
    var night : Double? = null,
    var eve   : Double? = null,
    var morn  : Double? = null
)

data class OpenWeather (

    var id          : Int?    = null,
    var main        : String? = null,
    var description : String? = null,
    var icon        : String? = null
)

data class List<T>(

    var dt        : Int?               = null,
    var sunrise   : Int?               = null,
    var sunset    : Int?               = null,
    var temp      : Temp?              = Temp(),
    var feelsLike : FeelsLike?         = FeelsLike(),
    var pressure  : Int?               = null,
    var humidity  : Int?               = null,
    var weather   : ArrayList<OpenWeather> = arrayListOf(),
    var speed     : Double?            = null,
    var deg       : Int?               = null,
    var gust      : Double?            = null,
    var clouds    : Int?               = null,
    var pop       : Int?               = null,
    var rain      : Double?            = null
)