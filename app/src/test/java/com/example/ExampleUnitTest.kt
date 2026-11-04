package com.example

import com.example.ui.ComparisonViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testPriceComparisonLogic() {
        val viewModel = ComparisonViewModel()

        // Item 1: 500g for 50 thousand tomans -> 100 thousand tomans per kilo
        viewModel.updateX1("500")
        viewModel.updateX2("50")

        // Item 2: 800g for 64 thousand tomans -> 80 thousand tomans per kilo
        viewModel.updateY1("800")
        viewModel.updateY2("64")

        val state = viewModel.uiState.value
        assertTrue(state.isCalculated)
        assertEquals(100.0, state.xkilo, 0.001)
        assertEquals(80.0, state.ykilo, 0.001)
        assertEquals("کالا دوم", state.winnerLabel)
        assertEquals("کالا اول", state.otherLabel)

        // 80 / 100 = 0.8 -> 20% cheaper
        assertEquals(20.0, state.percent, 0.001)
    }

    @Test
    fun testClearAllResetsState() {
        val viewModel = ComparisonViewModel()
        viewModel.updateX1("500")
        viewModel.updateX2("50")
        viewModel.updateY1("800")
        viewModel.updateY2("64")

        viewModel.clearAll()

        val state = viewModel.uiState.value
        assertEquals("", state.x1)
        assertEquals("", state.x2)
        assertEquals("", state.y1)
        assertEquals("", state.y2)
        assertFalse(state.isCalculated)
    }
}
