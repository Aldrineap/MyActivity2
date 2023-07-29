package com.alarcon.myprojectlocator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.alarcon.myprojectlocator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        if (isLoggedIn()) {

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val userName = binding.loginEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()


            if (databaseHelper.authenticateLogin(userName, password)) {
                showLoginNotification()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid credentials. Please try again.")
            }
        }

        binding.forgotPass.setOnClickListener {
            val intent = Intent(this, PasswordRecovery::class.java)
            startActivity(intent)
        }
        binding.textView8.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGoogle.setOnClickListener {
            // TODO: Implement Google Sign-In logic here
            showToast("Google Sign-In feature coming soon!")
        }

        binding.buttonFacebook.setOnClickListener {
            // TODO: Implement Facebook Login logic here
            showToast("Facebook Login feature coming soon!")
        }

        binding.passwordlayout.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibilityMainActivity(isPasswordVisible)
        }
    }

    private fun isLoggedIn(): Boolean {

        return false
    }

    private fun authenticateLogin(userName: String, password: String): Boolean {
        val user = databaseHelper.getUserByEmailAndPassword(userName, password)
        return user != null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoginNotification() {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_check)
            .setContentTitle("Login Successful")
            .setContentText("Welcome back! You are now logged in.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, DashboardActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Login Channel"
            val descriptionText = "Channel for login notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun togglePasswordVisibilityMainActivity(isVisible: Boolean) {
        val passwordEditText = binding.editTextTextPassword
        if (isVisible) {
            passwordEditText.transformationMethod = null
        } else {
            passwordEditText.transformationMethod =
                PasswordTransformationMethod()
        }
        passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
    }

    companion object {
        private const val CHANNEL_ID = "registration_channel"
        private const val NOTIFICATION_ID = 1
    }
}
