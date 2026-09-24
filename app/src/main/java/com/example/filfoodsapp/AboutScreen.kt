package com.example.filfoodsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Reusing established colors
private val FilBrand = Color(0xFF1A432B)
private val FilMint = Color(0xFFD4F0D6)
private val FilMuted = Color(0xFF7D9483)

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Top Mint Hero Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FilMint)
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Care",
                tint = FilBrand,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Food that cares.\nNutrition that heals.",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = FilBrand,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We believe recovery food should never be bland — so we combine flavor, nutrition, and care in every meal.",
                fontFamily = FontFamily.Serif,
                fontSize = 14.sp,
                color = FilMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        // --- Middle Content Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Our story",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = FilBrand
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "F.I.L. started with hospital kitchens that had to choose between taste and nutrition. We build meals that don't ask you to choose — created with dietitians, cooked by chefs, and delivered fresh to your door.",
                fontFamily = FontFamily.Serif,
                fontSize = 14.sp,
                color = FilMuted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Features List
            AboutFeatureItem(
                icon = Icons.Outlined.MedicalServices,
                title = "Doctor & dietitian designed",
                description = "Every recipe is reviewed against real dietary and medical guidelines."
            )
            AboutFeatureItem(
                icon = Icons.Outlined.Restaurant,
                title = "Cooked by professional chefs",
                description = "Trained culinary teams prepare each meal fresh, never bland."
            )
            AboutFeatureItem(
                icon = Icons.Outlined.Lightbulb,
                title = "Sourced with quality in mind",
                description = "Fresh ingredients, checked daily for freshness and taste."
            )
            AboutFeatureItem(
                icon = Icons.Outlined.CheckCircle,
                title = "Reviewed for your goals",
                description = "Every meal is checked by registered dietitians for your health needs."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Bottom Trusted Card ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(FilBrand)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Trusted by hospitals and loved by patients",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simple grid approximation for the hospital tags
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("[Hospital]", color = FilMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("[Hospital]", color = FilMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("[Hospital]", color = FilMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("[Hospital]", color = FilMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("[Hospital]", color = FilMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AboutFeatureItem(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(FilMint),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = FilBrand,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FilBrand
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontFamily = FontFamily.Serif,
                fontSize = 13.sp,
                color = FilMuted,
                lineHeight = 18.sp
            )
        }
    }
}