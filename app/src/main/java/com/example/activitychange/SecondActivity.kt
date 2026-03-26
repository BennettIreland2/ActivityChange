package com.example.activitychange

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun WelcomePage(username: String) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var homeAddress by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Welcome back: $username")

        Spacer(modifier = Modifier.height(16.dp))

        // Phone number
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
        }) {
            Text("Call")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$emailAddress")
            }
            context.startActivity(intent)
        }) {
            Text("Send Email")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Home address
        TextField(
            value = homeAddress,
            onValueChange = { homeAddress = it },
            label = { Text("Home Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=${Uri.encode(homeAddress)}")
            context.startActivity(intent)
        }) {
            Text("Open Map")
        }
    }
}