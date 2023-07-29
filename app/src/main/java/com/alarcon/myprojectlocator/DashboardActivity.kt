package com.alarcon.myprojectlocator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alarcon.myprojectlocator.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.MedicalView.setOnClickListener {
            val intent = Intent(this, HospitalActivity::class.java)
            startActivity(intent)
        }

        binding.FireView.setOnClickListener {
            callEmergencyDepartment("fire")
        }

        binding.PoliceView.setOnClickListener {
            callEmergencyDepartment("police")
        }


        binding.HomeEmergency.setOnClickListener {
            navigateToMainActivity()
        }


        binding.AcctInfoEmergency.setOnClickListener {
            navigateToAccountInfo()
        }
    }

    private fun callEmergencyDepartment(department: String) {
        val phoneNumber = getEmergencyPhoneNumber(department)
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(callIntent)
    }

    private fun getEmergencyPhoneNumber(department: String): String {
            return when (department) {
            "medical" -> "123"
            "fire" -> "456"
            "police" -> "789"
            else -> ""
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAccountInfo() {
        val intent = Intent(this,AccountInfoUser::class.java)
        startActivity(intent)
    }
}
