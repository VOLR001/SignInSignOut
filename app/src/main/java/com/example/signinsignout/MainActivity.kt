package com.example.signinsignout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signinsignout.ui.theme.SignInsignOutTheme
import com.example.signinsignout.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInsignOutTheme {
                //set up the NavController & NavHost for navigation in the program
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { MainView(navController) }
                        composable("signIn") { SignInView() }
                        composable("signUp") { SignUpView() }
                    }
                }
            }
        }
    }
}

@Composable
fun MainView(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // circle Icon
        Box(
            modifier = Modifier
                .size(128.dp) //size of the circle
                .clip(CircleShape)
                //make it circular
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape))
        {
            Image(
                painter = painterResource(id = R.drawable.cam),               contentDescription = "Our App Icon",
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //sign In Button
        Button(onClick = {
            // Nav to the signin screen
            navController.navigate("signIn")
            Toast.makeText(context, "Sign In Clicked", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //sign Up Button
        Button(onClick = {
            //nav to the sign-up screen
            navController.navigate("signUp")
            Toast.makeText(context, "Sign Up Clicked", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun SignInView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign In Screen")
    }
}

@Composable
fun SignUpView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    SignInsignOutTheme {
        MainView(navController = rememberNavController())
    }
}