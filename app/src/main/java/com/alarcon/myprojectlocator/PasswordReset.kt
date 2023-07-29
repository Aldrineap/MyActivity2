package com.alarcon.myprojectlocator

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alarcon.myprojectlocator.databinding.ResetPasswordBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*

class PasswordReset : AppCompatActivity() {
    private lateinit var binding: ResetPasswordBinding
    private var isPasswordVisible = false
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val email = intent.getStringExtra("email")

        binding.ButtonReset.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (password == confirmPassword) {
                showToast("Password reset successful. Please log in again.")

                 if (email != null) {
                    databaseHelper.updatePassword(email, password)
                }

                navigateToMainActivityAfterDelay()
            } else {
                showToast("Passwords do not match. Please try again.")
            }
        }

        binding.passwordInputLayout.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(isPasswordVisible, binding.passwordEditText)
            updatePasswordToggleIcon(isPasswordVisible)
        }

        binding.confirmPasswordInputLayout.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(isPasswordVisible, binding.confirmPasswordEditText)
            updatePasswordToggleIcon(isPasswordVisible)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun togglePasswordVisibility(isVisible: Boolean, editText: TextInputEditText) {
        if (isVisible) {
            editText.transformationMethod = null
        } else {
            editText.transformationMethod = PasswordTransformationMethod()
        }
        editText.setSelection(editText.text?.length ?: 0)
    }

    private fun updatePasswordToggleIcon(isVisible: Boolean) {
        val toggleIcon = if (isVisible) R.drawable.ic_eye1 else R.drawable.ic_eye2
        binding.passwordInputLayout.endIconDrawable = ContextCompat.getDrawable(this, toggleIcon)
        binding.confirmPasswordInputLayout.endIconDrawable = ContextCompat.getDrawable(this, toggleIcon)
    }

    private fun navigateToMainActivityAfterDelay() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val intent = Intent(this@PasswordReset, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
