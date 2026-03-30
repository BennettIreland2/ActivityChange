package com.example.activitychange

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.activitychange.ui.theme.ActivityChangeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        val startDestination = intent?.data?.let { uri ->
            when (uri.host) {
                "login" -> "login"
                else -> "login"
            }
        } ?: "login"
        setContent {
            ActivityChangeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()


                    }
                }
            }
        }
    }
}
object UserRepository {
    var username: String = ""
    var phone: String = ""
    var email: String = ""
    var address: String = ""
}

@Composable
fun LoginPage(onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Store the values in the repository
            UserRepository.username = username
            UserRepository.phone = "1234567890"
            UserRepository.email = "example@mail.com"
            UserRepository.address = "123 Street"
            onLogin()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        // Login page
        composable("login") {
            LoginPage {
                navController.navigate("profile")
            }
        }

        // Profile/Welcome page
        composable(
            "profile",
            deepLinks = listOf(
                androidx.navigation.navDeepLink {
                    uriPattern =
                        "activitychange://profile?username={username}&phone={phone}&email={email}&address={address}"
                }
            )
        ) { backStackEntry ->

            // Get the deep link URI if present
            val deepLinkUri = backStackEntry.arguments?.getParcelable<Intent>("android-support-nav:controller:deepLinkIntent")?.data
                ?: Uri.EMPTY

            // Extract query parameters if deep link exists
            val username = deepLinkUri.getQueryParameter("username") ?: UserRepository.username
            val phone = deepLinkUri.getQueryParameter("phone") ?: UserRepository.phone
            val email = deepLinkUri.getQueryParameter("email") ?: UserRepository.email
            val address = deepLinkUri.getQueryParameter("address") ?: UserRepository.address

            // Store in repository
            UserRepository.username = username
            UserRepository.phone = phone
            UserRepository.email = email
            UserRepository.address = address

            // Call your existing WelcomePage
            WelcomePage(
                UserRepository.username,
                UserRepository.phone,
                UserRepository.email,
                UserRepository.address
            )
        }
    }
}

