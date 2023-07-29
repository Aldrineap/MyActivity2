package com.alarcon.myprojectlocator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alarcon.myprojectlocator.databinding.ActivityHospitalBinding

class HospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hospitalList = createHospitalList()


        val hospitalAdapter = HospitalAdapter(hospitalList, this)

        binding.hospitalRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HospitalActivity)
            adapter = hospitalAdapter
        }
    }

    private fun createHospitalList(): List<Hospital> {
        val hospitalList = mutableListOf<Hospital>()

        hospitalList.add(Hospital("Bicol Medical Center", "123 Concepcion Pequena Naga City Camarines Sur", "472-6125",
            "094-999-56781", R.drawable.ic_hospital1))
        hospitalList.add(Hospital("Hospital B", "456 Park Avenue", "555-5678", "999-2345", R.drawable.ic_hospital2))
        hospitalList.add(Hospital("Hospital A", "123 Main Street", "555-1234", "999-5678", R.drawable.ic_hospital3))
        hospitalList.add(Hospital("Hospital B", "456 Park Avenue", "555-5678", "999-2345", R.drawable.ic_hospital4))
        hospitalList.add(Hospital("Hospital A", "123 Main Street", "555-1234", "999-5678", R.drawable.ic_hospital2))
        hospitalList.add(Hospital("Hospital B", "456 Park Avenue", "555-5678", "999-2345", R.drawable.ic_hospital1))
        hospitalList.add(Hospital("Hospital A", "123 Main Street", "555-1234", "999-5678", R.drawable.ic_hospital3))
        hospitalList.add(Hospital("Hospital B", "456 Park Avenue", "555-5678", "999-2345", R.drawable.ic_hospital4))
        // Add more hospitals as needed

        return hospitalList
    }

    fun onHospitalItemClick(hospital: Hospital) {
        val phoneNumber = hospital.mobilePhoneNumber
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialIntent)
    }

}
