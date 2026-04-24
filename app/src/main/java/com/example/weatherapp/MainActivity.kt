package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherUI()
        }
    }
}

@Composable
fun WeatherUI() {

    val scope = rememberCoroutineScope()

    var city by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("--") }
    var condition by remember { mutableStateOf("--") }
    var humidity by remember { mutableStateOf("--") }
    var wind by remember { mutableStateOf("--") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Weather App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true

                    try {
                        val response = RetrofitInstance.api.getWeather(
                            city,
                            API_KEY
                        )

                        temperature = "${response.main.temp}°C"
                        condition = response.weather[0].main
                        humidity = "${response.main.humidity}%"
                        wind = "${response.wind.speed} km/h"

                    } catch (e: Exception) {
                        condition = "Error: ${e.message}"
                    }

                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(25.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Temperature: $temperature")
                    Text("Condition: $condition")
                    Text("Humidity: $humidity")
                    Text("Wind: $wind")
                }
            }
        }
    }
}

