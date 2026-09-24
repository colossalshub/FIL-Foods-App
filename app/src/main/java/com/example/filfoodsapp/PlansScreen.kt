package com.example.filfoodsapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Reusing your established theme colors
private val FilBrand = Color(0xFF1A432B)      // Dark green for text and active buttons
private val FilMintBorder = Color(0xFF6CCB84) // Light green for the card border
private val FilMuted = Color(0xFF7D9483)      // Muted gray-green for secondary text

// [INTEGRATION POINT] - Backend API Data Model
// When connected to the Python backend, this matches the JSON response from your /api/plans endpoint.
data class SubscriptionPlan(
    val id: String,
    val tabName: String,
    val title: String,
    val price: String,
    val period: String,
    val description: String,
    val benefits: List<String>,
    val buttonText: String
)

@Composable
fun PlansScreen() {
    // Mock data mimicking a backend response
    val availablePlans = remember {
        listOf(
            SubscriptionPlan(
                id = "weekly",
                tabName = "Weekly",
                title = "Weekly plan",
                price = "₱1,890",
                period = "/ week",
                description = "Great for short-term needs",
                benefits = listOf(
                    "Customized meals for your goals",
                    "Dietitian-approved recipes",
                    "5% off on all orders"
                ),
                buttonText = "Choose weekly"
            ),
            SubscriptionPlan(
                id = "monthly",
                tabName = "Monthly",
                title = "Monthly plan",
                price = "₱7,200",
                period = "/ month",
                description = "Best value for steady progress",
                benefits = listOf(
                    "Customized meals for your goals",
                    "Dietitian-approved recipes",
                    "10% off on all orders",
                    "Free weekend dessert"
                ),
                buttonText = "Choose monthly"
            ),
            SubscriptionPlan(
                id = "yearly",
                tabName = "Yearly",
                title = "Yearly plan",
                price = "₱80,000",
                period = "/ year",
                description = "Ultimate commitment to health",
                benefits = listOf(
                    "Customized meals for your goals",
                    "Dietitian-approved recipes",
                    "15% off on all orders",
                    "Priority delivery routing"
                ),
                buttonText = "Choose yearly"
            )
        )
    }

    // State machine tracking which plan is currently viewed
    var selectedPlan by remember { mutableStateOf(availablePlans.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. Header Section
        Text(
            text = "Simple plans.\nPowerful benefits.",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = FilBrand,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "No lock-in. Change or cancel your\nplan anytime.",
            fontFamily = FontFamily.Serif,
            fontSize = 14.sp,
            color = FilMuted,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 2. Dynamic Toggle Bar
        PlanToggleBar(
            plans = availablePlans,
            selectedPlan = selectedPlan,
            onPlanSelected = { selectedPlan = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Plan Details Card
        PlanCard(plan = selectedPlan)

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Footer Note
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = FilMuted,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "No lock-in — change or cancel anytime",
                fontFamily = FontFamily.Serif,
                fontSize = 12.sp,
                color = FilMuted,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PlanToggleBar(
    plans: List<SubscriptionPlan>,
    selectedPlan: SubscriptionPlan,
    onPlanSelected: (SubscriptionPlan) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .border(1.dp, FilBrand, RoundedCornerShape(50))
            .background(Color.White)
            .padding(4.dp), // <--- THIS ADDED PADDING CREATES THE BREATHER ROOM AROUND THE PILLS
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        plans.forEach { plan ->
            val isSelected = plan == selectedPlan
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) FilBrand else Color.Transparent)
                    .clickable { onPlanSelected(plan) }
                    .padding(vertical = 10.dp), // Comfortable internal padding for text
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = plan.tabName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else FilBrand
                )
            }
        }
    }
}

@Composable
private fun PlanCard(plan: SubscriptionPlan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, FilMintBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = plan.title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = FilBrand
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stylized Price string (Large price, small period)
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = FilBrand)) {
                        append(plan.price)
                    }
                    withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = FilBrand)) {
                        append(plan.period)
                    }
                },
                fontFamily = FontFamily.Serif
            )

            Text(
                text = plan.description,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = FilBrand
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Dynamically generate benefits list
            plan.benefits.forEach { benefit ->
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Included",
                        tint = FilBrand,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = benefit,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = FilBrand
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CTA Button
            Button(
                onClick = { /* [INTEGRATION POINT] Trigger Order ViewModel state */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FilBrand),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = plan.buttonText,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}