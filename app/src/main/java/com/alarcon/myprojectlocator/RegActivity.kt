package com.alarcon.myprojectlocator

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.alarcon.myprojectlocator.databinding.ActivityRegBinding
import com.google.android.material.textfield.TextInputEditText
import android.text.method.PasswordTransformationMethod

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accountPasswordLayout.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibilityRegActivity(isPasswordVisible)
        }

        binding.button2.setOnClickListener {
            val fullName = binding.NameUser.text.toString()
            val email = binding.UserEmail.text.toString()
            val password = binding.accountPassword.text.toString()
            val confirmPassword = binding.accountPassword2.text.toString()


            if (validateRegistration(fullName, email, password, confirmPassword)) {

                showToast("Registration successful!")
                showNotification()
                showCheckImage()


                val user = User(fullName = fullName, email = email, password = password)
                val databaseHelper = DatabaseHelper(this)
                val userId = databaseHelper.addUser(user)

                 if (userId != -1L) {
                    showToast("User account saved to database with ID: $userId")
                } else {
                    showToast("Failed to save user account to database.")
                }

                setResult(Activity.RESULT_OK)
                finish()

                // Call the function to initialize password visibility
                togglePasswordVisibilityRegActivity(isPasswordVisible)

            } else {

                showToast("Registration failed. Please check your inputs.")
            }
        }
    }

    private fun validateRegistration(fullName: String, email: String, password: String, confirmPassword: String): Boolean {

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill in all fields.")
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email format.")
            return false
        }

        if (password != confirmPassword) {
            showToast("Passwords do not match.")
            return false
        }
        return true
    }

    private fun showToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showCheckImage() {
        binding.imageView2.visibility = View.VISIBLE
    }

    private fun showNotification() {

        createNotificationChannel()

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_check)
            .setContentTitle("Registration Successful")
            .setContentText("You're registered! Congratulations")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)

         val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Registration Channel"
            val descriptionText = "Channel for registration notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

             val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun togglePasswordVisibilityRegActivity(isVisible: Boolean) {
        val passwordEditText = findViewById<TextInputEditText>(R.id.accountPassword)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.accountPassword2)

        if (isVisible) {
            passwordEditText.transformationMethod = null
            confirmPasswordEditText.transformationMethod = null
        } else {
            passwordEditText.transformationMethod = PasswordTransformationMethod()
            confirmPasswordEditText.transformationMethod = PasswordTransformationMethod()
        }

        passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
        confirmPasswordEditText.setSelection(confirmPasswordEditText.text?.length ?: 0)
    }



    companion object {
        private const val CHANNEL_ID = "registration_channel"
        private const val NOTIFICATION_ID = 1
    }
}