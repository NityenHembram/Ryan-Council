package com.amvlabs.ryancouncil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.amvlabs.ryancouncil.databinding.FragmentStudentBinding
import com.amvlabs.ryancouncil.model.ComplainModel
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class StudentFragment : Fragment() {
    lateinit var binding:FragmentStudentBinding
    lateinit var db:FirebaseFirestore
    var name:String = ""
    var uid:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentBinding.inflate(layoutInflater)
        db = Firebase.firestore
        readBundle()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        binding.etUserName.text = name
        ArrayAdapter.createFromResource(requireContext(), R.array.planets_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        binding.complainSubmitBtn.setOnClickListener { submit() }
    }

    private fun submit() {
        val complain = binding.etComplain.text.toString()
        val catagory = binding.spinner.selectedItem.toString()
        if(uid.isNotEmpty() && name.isNotEmpty()) {
            val com = ComplainModel(name,complain,catagory)
            db.collection("complains").document("${name}_$uid").set(com).addOnSuccessListener {
                binding.etComplain.text?.clear()
                Utility.showToast(requireContext(),"Complain Successfully added.")
            }.addOnFailureListener {
                Utility.showToast(requireContext(),"Some Error Occur")
            }
        }
    }

    private fun readBundle() {
        val userDetails = Global.getUserDetails()
        name = userDetails?.name.toString()
        uid = userDetails?.uid.toString()
    }

}