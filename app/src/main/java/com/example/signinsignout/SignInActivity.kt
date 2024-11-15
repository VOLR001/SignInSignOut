package com.example.signinsignout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.signinsignout.ui.theme.SignInsignOutTheme
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.android.ads.mediationtestsuite.activities.HomeActivity
import com.google.firebase.auth.FirebaseUser

class SignInActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //create Firebase instance
        auth = FirebaseAuth.getInstance()

        setContent {
            SignInsignOutTheme {
                SignInScreen()
            }
        }
    }

    @Composable
    fun SignInScreen() {
        //declare states for email, password, and PIN inputs
        val emailEditText = remember { mutableStateOf("") }
        val passwordEditText = remember { mutableStateOf("") }
        val pinEditText = remember { mutableStateOf("") } //add a PIN input field

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //email input field using TextField
            TextField(
                value = emailEditText.value,
                onValueChange = { emailEditText.value = it },
                placeholder = { Text("Enter Email") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            //password input field
            TextField(
                value = passwordEditText.value,
                onValueChange = { passwordEditText.value = it },
                placeholder = { Text("Enter Password") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
                //for password field masking
            )

            Spacer(modifier = Modifier.height(16.dp))

            //PIN input field to securely store the user PIN
            TextField(
                value = pinEditText.value,
                onValueChange = { pinEditText.value = it },
                placeholder = { Text("Enter PIN") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("PIN") },
                visualTransformation = PasswordVisualTransformation()
            //mask the PIN field
            )

            Spacer(modifier = Modifier.height(16.dp))

            //signIn Button
            Button(onClick = {
                val email = emailEditText.value
                val password = passwordEditText.value
                val pin = pinEditText.value //read the entered PIN

                if (email.isEmpty() || password.isEmpty() || pin.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    signInUser(email, password, pin) //pass PIN to sign-in logic
                }
            }) {
                Text("Sign In")
            }

            Spacer(modifier = Modifier.height(16.dp))

            //signUp navigation link
            Text(
                text = "Don't have an account? Sign Up",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    //navigate to SignUp screen view
                    startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
                }
            )
        }
    }

    private fun signInUser(email: String, password: String, pin: String) {
        //Toast message before attempting sign-in
        Toast.makeText(this, "Signing in...", Toast.LENGTH_SHORT).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //sign-in successful -> collect the user info
                    val user = auth.currentUser
                    if (user != null) {
                        saveUserDetails(user, pin) //pass PIN to saveUserDetails
                    }
                } else {
                    //if sign-in fails, display a message to the user
                    Toast.makeText(this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserDetails(user: FirebaseUser, pin: String) {
        // show Toast message indicating that user details are being saved
        Toast.makeText(this, "Saving user details...", Toast.LENGTH_SHORT).show()

        // retrieve the user's unique ID (UID)
        val userUid = user.uid
        val database = FirebaseDatabase.getInstance().reference


        val userDetails = hashMapOf(
            "pin" to pin, //store the actual PIN entered by the user
            "firebase_url" to database.toString()
        )

        //save details in Firebase under users node
        database.child("users").child(userUid).setValue(userDetails)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //show success message upon saving user details
                    Toast.makeText(this, "User details saved!", Toast.LENGTH_SHORT).show()
                    //navigate to the next screen after successful login
                    startActivity(Intent(this, HomeActivity::class.java)) // Redirect to the home screen
                    finish()
                } else {
                    //show error message if saving user details fails
                    Toast.makeText(this, "Error saving details. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}