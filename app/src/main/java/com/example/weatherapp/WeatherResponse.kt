
package com.example.weatherapp

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Weather(
    val main: String
)

data class Wind(
    val speed: Float
)