package com.example.filfoodsapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filfoodsapp.ui.theme.FILFoodsAppTheme
import androidx.compose.ui.layout.ContentScale

// Colors are local to this file so nothing depends on the theme's palette.
// Later you can move them into ui/theme/Color.kt.
private val FilInk = Color(0xFF0B1F14)        // near-black green: chips, buttons, body text
private val FilBrand = Color(0xFF1A432B)      // dark green: headings
private val FilLogoGreen = Color(0xFF237A45)  // logo tile + bell
private val FilMint = Color(0xFFD4F0D6)       // card backgrounds
private val FilMintBorder = Color(0xFF6CCB84) // featured card outline
private val FilMuted = Color(0xFF7D9483)      // greeting subtitle

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        HomeHeader()

        Spacer(Modifier.height(20.dp))
        Greeting()

        Spacer(Modifier.height(20.dp))
        SearchBar()

        Spacer(Modifier.height(14.dp))
        DietaryTags()

        Spacer(Modifier.height(20.dp))
        FeaturedDish()

        Spacer(Modifier.height(24.dp))
        WhyFil()

        Spacer(Modifier.height(24.dp))
        ReadyToEatCta()

        Spacer(Modifier.height(16.dp))
    }
}

// ---------- Header ----------

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // New Image Composable replacing the Box
        Image(
            painter = painterResource(id = R.drawable.ic_logo_pot),
            contentDescription = "F.I.L. Foods Logo",
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FilLogoGreen)
        )

        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("Food is Life", fontSize = 20.sp, color = Color(0xFF333333))
            Text("Balanced nutrition, made simple.", fontSize = 9.sp, color = Color.Gray)
        }

        IconButton(onClick = { /* TODO: notifications */ }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = FilLogoGreen
            )
        }
    }
}

// ---------- Greeting ----------

@Composable
private fun Greeting() {
    Text(
        text = "Good Morning, Hanna",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = FilBrand
    )
    Spacer(Modifier.height(10.dp))
    Text(
        text = "Your next meal delivery arrives tomorrow,\n8–10 AM.",
        fontFamily = FontFamily.Serif,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = FilMuted
    )
}

// ---------- Search ----------

@Composable
private fun SearchBar() {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search meals...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FilInk,
            unfocusedBorderColor = FilInk,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = FilInk,
            unfocusedTextColor = FilInk,
            cursorColor = FilInk
        )
    )
}

// ---------- Dietary chips ----------

@Composable
private fun DietaryTags() {
    val tags = listOf("Low sodium", "Diabetic friendly", "High protein")
    var selected by remember { mutableStateOf(tags.first()) }

    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            DietaryChip(label = tag, selected = tag == selected) { selected = tag }
        }
    }
}

@Composable
private fun DietaryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(50)
    Box(
        modifier = Modifier
            .clip(shape)
            .background(if (selected) FilInk else Color.White)
            .border(1.dp, FilInk, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = if (selected) Color.White else FilInk
        )
    }
}

// ---------- Featured dish ----------

@Composable
private fun FeaturedDish() {
    Text(
        text = "Featured Dish",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = FilBrand
    )
    Spacer(Modifier.height(10.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FilMint),
        border = BorderStroke(1.dp, FilMintBorder)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // The outer Box holds both the image and the "Chef's Pick" tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                // 1. Your Food Image
                Image(
                    painter = painterResource(id = R.drawable.img_salmon),
                    contentDescription = "Grilled Salmon & Veggies",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 2. The "Chef's Pick" tag floating on top
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(FilBrand)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Chef's Pick", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Grilled Salmon & Veggies",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = FilInk
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Dietitian-approved seasoned brown rice with low-sodium fresh wild salmon.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = FilInk
            )

            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(text = "🔥 420 kcal", filled = true)
                InfoTag(text = "Low Sodium", filled = false)
            }
        }
    }
}

@Composable
private fun InfoTag(text: String, filled: Boolean) {
    val shape = RoundedCornerShape(50)
    Text(
        text = text,
        fontSize = 11.sp,
        color = if (filled) Color.White else FilInk,
        modifier = Modifier
            .clip(shape)
            .background(if (filled) FilInk else Color.White)
            .border(1.dp, FilInk, shape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

// ---------- Why F.I.L. ----------

@Composable
private fun WhyFil() {
    Text(
        text = "Why F.I.L.",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = FilBrand
    )
    Spacer(Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FeatureItem(icon = "👩‍️", label = "Real\ndietitian")
        FeatureItem(icon = "👨‍", label = "Made by\nprofessional\nchefs")
        FeatureItem(icon = "🤍", label = "Made with\ncare")
    }
}

@Composable
private fun FeatureItem(icon: String, label: String) {
    Column(
        modifier = Modifier.width(96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(FilMint),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 26.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

// ---------- CTA ----------

@Composable
private fun ReadyToEatCta() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FilMint)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ready to eat better?",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = FilInk
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Pick a plan that fits your recovery, your schedule, and your taste.",
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center,
                color = FilInk
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: navigate to Plans */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = FilInk),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "See plans",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1100)
@Composable
private fun HomeScreenPreview() {
    FILFoodsAppTheme {
        HomeScreen()
    }
}