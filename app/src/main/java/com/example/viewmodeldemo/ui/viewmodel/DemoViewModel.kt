package com.example.viewmodeldemo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.roundToInt

class DemoViewModel {

    var isFahrenheit by mutableStateOf(false)
    var result by mutableStateOf("0")

    fun convertTemp(temp: String) {
        try {
            val tempInt = temp.toInt()
            result = if (isFahrenheit) {
                ( (tempInt - 32) * 0.5556 ).roundToInt().toString()
            } else {
                ( (tempInt * 1.8) + 32 ).roundToInt().toString()
            }
        } catch(e: Exception) {
            result = "Entrada inválida!"
        }
    }
    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }
}

