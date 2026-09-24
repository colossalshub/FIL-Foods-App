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
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

// Reusing your established theme colors
private val FilInk = Color(0xFF0B1F14)        // Almost black
private val FilBrand = Color(0xFF1A432B)      // Dark green
private val FilLogoGreen = Color(0xFF237A45)  // Logo green
private val FilMint = Color(0xFFD4F0D6)       // Light mint background
private val FilMuted = Color(0xFF7D9483)      // Gray-green text
private val FilOutline = Color(0xFFE0E0E0)    // Light gray border

@Composable
fun AccountScreen() {
    // STATE: This single boolean controls the entire page view
    var isSignIn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // 1. Header
        Image(
            painter = painterResource(id = R.drawable.ic_logo_pot), // Match your logo image filename in res/drawable!
            contentDescription = "F.I.L. Foods Logo",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(FilLogoGreen)
                .padding(8.dp) // Adjust padding if you want more/less breathing room inside the green tile
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome Back!",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = FilBrand
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to manage your meal plan and\ndeliveries.",
            fontFamily = FontFamily.Serif,
            fontSize = 14.sp,
            color = FilMuted,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. The Toggle Switch (Sign in / Sign up)
        AuthToggleBar(
            isSignIn = isSignIn,
            onToggle = { isSignIn = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Dynamic Form Content
        if (isSignIn) {
            SignInForm()
        } else {
            SignUpForm()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Social Logins & Footer
        SocialLoginsSection(
            isSignIn = isSignIn,
            onFooterClick = { isSignIn = !isSignIn }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun AuthToggleBar(isSignIn: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(FilMint)
            .padding(4.dp), // <--- THIS CREATES THE BREATHER ROOM AROUND THE PILLS
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sign In Button
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50))
                .background(if (isSignIn) FilInk else Color.Transparent)
                .clickable { onToggle(true) }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign in",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (isSignIn) Color.White else FilBrand
            )
        }

        // Sign Up Button
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50))
                .background(if (!isSignIn) FilInk else Color.Transparent)
                .clickable { onToggle(false) }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign up",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (!isSignIn) Color.White else FilBrand
            )
        }
    }
}

@Composable
private fun SignInForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        AuthTextField(label = "Email/Phone number", placeholder = "you@email.com", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(label = "Password", placeholder = "••••••••", value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot password?",
            fontFamily = FontFamily.Serif,
            fontSize = 13.sp,
            color = FilMuted,
            modifier = Modifier.align(Alignment.End).clickable { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* [INTEGRATION POINT] Call Python Backend /api/auth/login */ },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FilInk),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign in", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun SignUpForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var planFor by remember { mutableStateOf("myself") }

    Column(modifier = Modifier.fillMaxWidth()) {
        AuthTextField(label = "Full name", placeholder = "Jetrick Roxas", value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(label = "Email/Phone Number", placeholder = "you@email.com", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(label = "Password", placeholder = "••••••••", value = password, onValueChange = { password = it }, isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(label = "Re-type password", placeholder = "••••••••", value = confirmPassword, onValueChange = { confirmPassword = it }, isPassword = true)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Who is this plan for?", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FilBrand)
        Text("Choose one — you can add more people later.", fontFamily = FontFamily.Serif, fontSize = 12.sp, color = FilMuted)

        Spacer(modifier = Modifier.height(12.dp))

        PlanForOption(
            title = "Myself",
            subtitle = "You'll order and manage your own meals.",
            isSelected = planFor == "myself",
            onClick = { planFor = "myself" }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PlanForOption(
            title = "A family member",
            subtitle = "Like a parent or grandparent — you'll order for them using this account.",
            isSelected = planFor == "family",
            onClick = { planFor = "family" }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirmation info box
        Row(
            modifier = Modifier.fillMaxWidth().border(1.dp, FilOutline, RoundedCornerShape(8.dp)).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = FilBrand, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Creating an account you'll manage as ", fontSize = 12.sp, color = FilMuted)
            Text(if (planFor == "myself") "yourself." else "a family member.", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FilBrand)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* [INTEGRATION POINT] Call Python Backend /api/auth/register */ },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FilInk),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Create account", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun AuthTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FilBrand)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray, fontSize = 14.sp) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FilBrand,
                unfocusedBorderColor = FilOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color.Gray
                        )
                    }
                }
            } else null
        )
    }
}

@Composable
private fun PlanForOption(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, if (isSelected) FilBrand else FilOutline, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = FilBrand, unselectedColor = Color.Gray)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FilBrand)
            Text(text = subtitle, fontFamily = FontFamily.Serif, fontSize = 12.sp, color = FilMuted)
        }
    }
}

@Composable
private fun SocialLoginsSection(isSignIn: Boolean, onFooterClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Divider
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = FilOutline)
            Text(" Or continue with ", fontSize = 12.sp, color = FilMuted, modifier = Modifier.padding(horizontal = 8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = FilOutline)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, FilOutline)
            ) {
                Text("Apple", color = FilInk, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, FilOutline)
            ) {
                Text("Google", color = FilInk, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Footer Text Toggle
        Row(modifier = Modifier.clickable { onFooterClick() }) {
            Text(
                text = if (isSignIn) "Don't have an account? " else "Already have an account? ",
                fontFamily = FontFamily.Serif,
                fontSize = 14.sp,
                color = FilMuted
            )
            Text(
                text = if (isSignIn) "Sign up" else "Sign in",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = FilInk
            )
        }
    }
}