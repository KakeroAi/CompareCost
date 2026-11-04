package com.example.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

data class ComparisonUiState(
    val x1: String = "", // Item 1 Weight (grams)
    val x2: String = "", // Item 1 Price (thousand tomans)
    val y1: String = "", // Item 2 Weight (grams)
    val y2: String = "", // Item 2 Price (thousand tomans)
    val isCalculated: Boolean = false,
    val xkilo: Double = 0.0,
    val ykilo: Double = 0.0,
    val winnerLabel: String = "", // "کالا اول" or "کالا دوم"
    val otherLabel: String = "",  // "کالا دوم" or "کالا اول"
    val isWinnerItem1: Boolean = true,
    val isEqual: Boolean = false,
    val percent: Double = 0.0     // Percentage cheaper
)

class ComparisonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ComparisonUiState())
    val uiState: StateFlow<ComparisonUiState> = _uiState.asStateFlow()

    fun updateX1(value: String) {
        val sanitized = sanitizeInput(value)
        _uiState.update { it.copy(x1 = sanitized) }
        calculateIfReady()
    }

    fun updateX2(value: String) {
        val sanitized = sanitizeInput(value)
        _uiState.update { it.copy(x2 = sanitized) }
        calculateIfReady()
    }

    fun updateY1(value: String) {
        val sanitized = sanitizeInput(value)
        _uiState.update { it.copy(y1 = sanitized) }
        calculateIfReady()
    }

    fun updateY2(value: String) {
        val sanitized = sanitizeInput(value)
        _uiState.update { it.copy(y2 = sanitized) }
        calculateIfReady()
    }

    private fun sanitizeInput(input: String): String {
        // Replace Persian/Arabic digits with English digits and keep numbers/dots
        return input.replace('۰', '0').replace('۱', '1').replace('۲', '2')
            .replace('۳', '3').replace('۴', '4').replace('۵', '5')
            .replace('۶', '6').replace('۷', '7').replace('۸', '8')
            .replace('۹', '9').replace(',', '.')
            .filter { it.isDigit() || it == '.' }
    }

    private fun calculateIfReady() {
        val state = _uiState.value
        val x1Val = state.x1.toDoubleOrNull()
        val x2Val = state.x2.toDoubleOrNull()
        val y1Val = state.y1.toDoubleOrNull()
        val y2Val = state.y2.toDoubleOrNull()

        if (x1Val != null && x1Val > 0 &&
            x2Val != null && x2Val >= 0 &&
            y1Val != null && y1Val > 0 &&
            y2Val != null && y2Val >= 0
        ) {
            val xkilo = (x2Val / x1Val) * 1000.0
            val ykilo = (y2Val / y1Val) * 1000.0

            val isWinner1 = xkilo < ykilo
            val isEqual = abs(xkilo - ykilo) < 0.00001

            val (winnerLabel, otherLabel, betterKilo, otherKilo) = when {
                isEqual -> Quadruple("هر دو کالا", "", xkilo, ykilo)
                isWinner1 -> Quadruple("کالا اول", "کالا دوم", xkilo, ykilo)
                else -> Quadruple("کالا دوم", "کالا اول", ykilo, xkilo)
            }

            val percent = if (!isEqual && otherKilo > 0) {
                // Formula from requirement: (better / other) - 1 -> percent
                val ratio = betterKilo / otherKilo
                abs((ratio - 1.0) * 100.0)
            } else {
                0.0
            }

            _uiState.update {
                it.copy(
                    isCalculated = true,
                    xkilo = xkilo,
                    ykilo = ykilo,
                    winnerLabel = winnerLabel,
                    otherLabel = otherLabel,
                    isWinnerItem1 = isWinner1,
                    isEqual = isEqual,
                    percent = percent
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isCalculated = false,
                    xkilo = 0.0,
                    ykilo = 0.0,
                    winnerLabel = "",
                    otherLabel = "",
                    percent = 0.0
                )
            }
        }
    }

    fun clearAll() {
        _uiState.value = ComparisonUiState()
    }
}

private data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)
