package com.example.viewmodeldemo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.roundToInt

class DemoViewModel {

    var isFahrenheit by mutableStateOf(true)
    var result by mutableStateOf("")

    fun convertTemperature(temp: String) {
        try {
            val temInt = temp.toInt()
            if (isFahrenheit) {
                result = ( (temInt - 32) * 0.5556 ).roundToInt().toString()
            } else {
                result = ( (temInt * 1.8) + 32 ).roundToInt().toString()
            }
        } catch(e: Exception) {
            result = "Entrada inválida!"
        }
    }
}