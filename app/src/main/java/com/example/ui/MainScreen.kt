package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.ResetRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TurquoiseBackground
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.WinnerBorderBlack
import com.example.ui.theme.WinnerGreen
import com.example.ui.theme.WinnerGreenContainer
import java.text.DecimalFormat

@Composable
fun MainScreen(
    viewModel: ComparisonViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val x1FocusRequester = remember { FocusRequester() }
    val x2FocusRequester = remember { FocusRequester() }
    val y1FocusRequester = remember { FocusRequester() }
    val y2FocusRequester = remember { FocusRequester() }

    // Auto focus x1 and open keyboard when app starts or clears
    LaunchedEffect(Unit) {
        x1FocusRequester.requestFocus()
        keyboardController?.show()
    }

    val decimalFormatter = remember { DecimalFormat("#,##0.##") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = TurquoiseBackground
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE0F7FA),
                                Color(0xFFF4FBFB),
                                Color(0xFFE0F2F1)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Header Area (Compact Top Bar)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            color = TurquoisePrimary.copy(alpha = 0.15f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.app_icon),
                                contentDescription = "آیکون اپلیکیشن",
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "مقایسه قیمت دو کالا",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = TurquoisePrimary
                                ),
                                modifier = Modifier.testTag("app_title")
                            )
                            Text(
                                text = "محاسبه ارزش خرید به ازای هر کیلوگرم",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp,
                                    color = TextPrimary.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    // Swapped Inputs Area: Card Item 2 then Card Item 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Card: Item 2 (کالا دوم)
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .testTag("item2_card"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Scale,
                                        contentDescription = null,
                                        tint = TurquoisePrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "کالا دوم",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp,
                                            color = TurquoisePrimary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Input y1: Weight (grams) with attached icon 1
                                CompactInputField(
                                    value = uiState.y1,
                                    onValueChange = { viewModel.updateY1(it) },
                                    label = "مقدار (گرم)",
                                    leadingIconRes = R.drawable.ic_weight,
                                    focusRequester = y1FocusRequester,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { y2FocusRequester.requestFocus() }
                                    ),
                                    testTag = "y1_input"
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Input y2: Price (thousand tomans) with attached icon 2
                                CompactInputField(
                                    value = uiState.y2,
                                    onValueChange = { viewModel.updateY2(it) },
                                    label = "قیمت (هزارتومان)",
                                    leadingIconRes = R.drawable.ic_price,
                                    focusRequester = y2FocusRequester,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            // Keep keyboard open as requested
                                        }
                                    ),
                                    testTag = "y2_input"
                                )
                            }
                        }

                        // Card: Item 1 (کالا اول)
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .testTag("item1_card"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = null,
                                        tint = TurquoisePrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "کالا اول",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp,
                                            color = TurquoisePrimary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Input x1: Weight (grams) with attached icon 1
                                CompactInputField(
                                    value = uiState.x1,
                                    onValueChange = { viewModel.updateX1(it) },
                                    label = "مقدار (گرم)",
                                    leadingIconRes = R.drawable.ic_weight,
                                    focusRequester = x1FocusRequester,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { x2FocusRequester.requestFocus() }
                                    ),
                                    testTag = "x1_input"
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Input x2: Price (thousand tomans) with attached icon 2
                                CompactInputField(
                                    value = uiState.x2,
                                    onValueChange = { viewModel.updateX2(it) },
                                    label = "قیمت (هزارتومان)",
                                    leadingIconRes = R.drawable.ic_price,
                                    focusRequester = x2FocusRequester,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { y1FocusRequester.requestFocus() }
                                    ),
                                    testTag = "x2_input"
                                )
                            }
                        }
                    }

                    // Calculation Results Section
                    AnimatedVisibility(
                        visible = uiState.isCalculated,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { 20 })
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .testTag("results_card"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Result Line 1: Best Item Header
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "پس ",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 19.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextPrimary
                                        )
                                    )

                                    // Winner Label styled as: "به رنگ سبز بولدر نازک مشکی و کمی سایز بزرگتر"
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(WinnerGreenContainer)
                                            .border(
                                                width = 1.2.dp,
                                                color = WinnerBorderBlack,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = uiState.winnerLabel,
                                            style = MaterialTheme.typography.headlineMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 22.sp,
                                                color = WinnerGreen
                                            ),
                                            modifier = Modifier.testTag("winner_label")
                                        )
                                    }

                                    Text(
                                        text = " بهتر هست",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 19.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextPrimary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Result Line 2: Percentage difference
                                if (!uiState.isEqual) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = WinnerGreenContainer.copy(alpha = 0.5f),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 10.dp
                                            ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = WinnerGreen,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "${uiState.winnerLabel} به مقدار ${decimalFormatter.format(uiState.percent)} درصد ارزان تر است.",
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 17.sp,
                                                    color = WinnerGreen
                                                ),
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.testTag("percent_result")
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(14.dp))
                                } else {
                                    Text(
                                        text = "قیمت و ارزش خرید هر دو کالا کاملاً برابر است.",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 17.sp,
                                            color = TurquoisePrimary
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                }

                                // Divider Line
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(TurquoisePrimary.copy(alpha = 0.15f))
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Result Line 3: Price per kg for Item 1
                                Text(
                                    text = "قیمت هرکیلو کالا اول ${decimalFormatter.format(uiState.xkilo)} هزار تومان است.",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 17.sp,
                                        color = TextPrimary
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("xkilo_result")
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Result Line 4: Price per kg for Item 2
                                Text(
                                    text = "قیمت هرکیلو کالا دوم ${decimalFormatter.format(uiState.ykilo)} هزار تومان است.",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 17.sp,
                                        color = TextPrimary
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ykilo_result")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Red Clear Data Button ("پاک کردن داده ها")
                    Button(
                        onClick = {
                            viewModel.clearAll()
                            x1FocusRequester.requestFocus()
                            keyboardController?.show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("clear_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ResetRed,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "پاک کردن داده ها",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "پاک کردن داده ها",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Footer / Designer Branding
                    Text(
                        text = "طراح اپلیکیشن: Kakero",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .testTag("designer_footer")
                    )
                }
            }
        }
    }
}

@Composable
private fun CompactInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIconRes: Int? = null,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    testTag: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .testTag(testTag),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
        },
        leadingIcon = leadingIconRes?.let { resId ->
            {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextPrimary
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TurquoisePrimary,
            unfocusedBorderColor = TurquoisePrimary.copy(alpha = 0.4f),
            focusedLabelColor = TurquoisePrimary,
            unfocusedLabelColor = TextPrimary.copy(alpha = 0.7f),
            focusedContainerColor = Color(0xFFFAFFFF),
            unfocusedContainerColor = Color(0xFFFAFAFA)
        )
    )
}
