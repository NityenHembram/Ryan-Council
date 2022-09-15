package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amvlabs.ryancouncil.databinding.ActivityRegistationBinding
import com.amvlabs.ryancouncil.dialog.LoadingDialog
import com.amvlabs.ryancouncil.model.Credential
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private val TAG = RegistrationActivity::class.java.name
    lateinit var binding:ActivityRegistationBinding
    lateinit var auth:FirebaseAuth
     var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regSubBtn.setOnClickListener { registration() }

        initialization()
    }
    private fun initialization() {
        auth = FirebaseAuth.getInstance()
    }
    private fun registration() {
        val name = binding.etName.text.trim().toString()
        val email = binding.regEtEmail.text.trim().toString()
        val password = binding.regEtPass.text.trim().toString()
        LoadingDialog.displayLoadingWithText(this, "please wait...", false)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val credential = Credential(name, email)
                Log.d(TAG, "registration: c;ocl")
                Utility.showToast(baseContext,"Registered")
                db.collection("user").document(it.user?.uid.toString()).set(credential).addOnSuccessListener {
                    LoadingDialog.hideLoading()
//                    Utility.showToast(baseContext,"Successful")
                    Log.d(TAG, "registration: $it")
                    startActivity(Intent(this, HomeActivity::class.java))
                }.addOnFailureListener { e ->
                    LoadingDialog.hideLoading()
                    Log.d(TAG, "registration: ${e.message}")
//                    Utility.showToast(baseContext,e.message.toString())
                }


            }.addOnFailureListener { t ->
                LoadingDialog.hideLoading()
//            Utility.showToast(baseContext,e.message.toString())
            }
    }

}