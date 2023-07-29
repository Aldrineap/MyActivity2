package com.alarcon.myprojectlocator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alarcon.myprojectlocator.databinding.ItemHospitalBinding

class HospitalAdapter(private val hospitalList: List<Hospital>, private val itemClickListener: HospitalActivity) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    interface ItemClickListener {
        fun onHospitalItemClick(hospital: Hospital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val binding = ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HospitalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val currentHospital = hospitalList[position]
        holder.bind(currentHospital)
        holder.itemView.setOnClickListener {
            itemClickListener.onHospitalItemClick(currentHospital)
        }
    }

    override fun getItemCount(): Int {
        return hospitalList.size
    }

    class HospitalViewHolder(private val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: Hospital) {
            binding.apply {
                itemHospitalNameTextView.text = hospital.name
                itemHospitalAddressTextView.text = hospital.address
                itemHospitalInfoNumberTextView.text = hospital.landPhoneNumber
                itemHospitalMobileNumberTextView.text = hospital.mobilePhoneNumber
                itemHospitalImageView.setImageResource(hospital.imageResourceId)
            }
        }
    }
}
