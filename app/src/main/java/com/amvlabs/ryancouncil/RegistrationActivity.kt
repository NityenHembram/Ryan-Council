package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.core.view.isVisible
import com.amvlabs.ryancouncil.databinding.ActivityRegistationBinding
import com.amvlabs.ryancouncil.dialog.LoadingDialog
import com.amvlabs.ryancouncil.model.Credential
import com.amvlabs.ryancouncil.model.UserDetails
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher

class RegistrationActivity : AppCompatActivity() {
    private val TAG = RegistrationActivity::class.java.name
    lateinit var binding: ActivityRegistationBinding
    lateinit var auth: FirebaseAuth
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
        val uid = ""
        if (name.isNotEmpty()) {
            if (email.isNotEmpty() && Utility.isEmailValid(email)) {
                if (password.isNotEmpty()) {
                    val intent = Intent(this, HomeActivity::class.java)
                    LoadingDialog.displayLoadingWithText(this, "please wait...", false)
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { it ->
                            uid = it.user?.uid.toString()
                            val credential = Credential(name, email)
                            Utility.showToast(baseContext, "Registered")
                            db.collection("user").document(it.user?.uid.toString()).set(credential)
                                .addOnSuccessListener { t ->
                                    LoadingDialog.hideLoading()
                                    Global.setUserDetails(UserDetails(uid,name))
                                    Utility.showToast(baseContext, "Successful")
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener { e ->
                                LoadingDialog.hideLoading()
                                Utility.showToast(baseContext, e.message.toString())
                            }
                        }.addOnFailureListener { t ->
                        LoadingDialog.hideLoading()
                        Utility.showToast(baseContext, t.message.toString())
                    }
                } else {
                    binding.passWarning.text = "Please enter password"
                    binding.passWarning.visibility = View.VISIBLE
                    if (binding.nameWarning.isVisible || binding.emailWarning.isVisible || binding.passWarning.isVisible)  {
                        binding.nameWarning.visibility = View.GONE
                        binding.emailWarning.visibility = View.GONE
                        binding.passWarning.visibility = View.GONE
                    }
                }
            } else {
                binding.emailWarning.text = "Please enter valid email"
                binding.emailWarning.visibility = View.VISIBLE
                if (binding.nameWarning.isVisible || binding.passWarning.isVisible) {
                    binding.nameWarning.visibility = View.GONE
                    binding.passWarning.visibility = View.GONE
                }
            }
        } else {
            binding.nameWarning.text = "Please enter name"
            binding.nameWarning.visibility = View.VISIBLE
            if (binding.emailWarning.isVisible || binding.passWarning.isVisible) {
                binding.emailWarning.visibility = View.GONE
                binding.passWarning.visibility = View.GONE
            }
        }

    }
}