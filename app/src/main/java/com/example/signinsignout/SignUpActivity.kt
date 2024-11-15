package com.example.signinsignout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.core.view.isVisible

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtFirstName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnUploadImage: Button
    private lateinit var btnSubmit: Button
    private lateinit var txtSignIn: TextView
    private lateinit var imageView: ImageView

    private var profileImageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // link xml

        //initialize UI elements
        edtFirstName = findViewById(R.id.edtFirstName)
        edtLastName = findViewById(R.id.edtLastName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        btnUploadImage = findViewById(R.id.btnUploadImage)
        btnSubmit = findViewById(R.id.btnSubmit)
        txtSignIn = findViewById(R.id.txtSignIn)
        imageView = findViewById(R.id.imageView) //show the profile picture

        //set up the profile picture upload button
        btnUploadImage.setOnClickListener {
            //open image picker to upload the photo
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        //set up the Sign Up button logic
        btnSubmit.setOnClickListener {
            if (validateForm()) {

                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                //show messagr and go to the next screen or SignIn
            } else {
                Toast.makeText(this, "Please fill out all fields and confirm passwords match.", Toast.LENGTH_SHORT).show()
            }
        }

        // set up the Sign In text click event
        txtSignIn.setOnClickListener {
            //navigate to the SignIn activity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        val firstName = edtFirstName.text.toString()
        val lastName = edtLastName.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()

        return firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
    }

    //image picker launcher
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                profileImageUri = selectedImageUri.toString()
                imageView.setImageURI(selectedImageUri)  // image in ImageView
            }
        }
}