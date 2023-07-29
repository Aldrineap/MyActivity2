package com.alarcon.myprojectlocator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alarcon.myprojectlocator.databinding.RecoveryPasswordBinding

class PasswordRecovery : AppCompatActivity() {

    private lateinit var binding: RecoveryPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecoveryPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

               binding.button3.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()


            if (validateEmail(email)) {

                showToast("Password recovery email sent to: $email")

                val intent = Intent(this, PasswordReset::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            } else {

                showToast("Invalid email. Please enter a valid email address.")
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
               return email.contains("@")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
