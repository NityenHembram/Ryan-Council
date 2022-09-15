package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amvlabs.ryancouncil.databinding.ActivityLoginBinding
import com.amvlabs.ryancouncil.dialog.LoadingDialog
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.regBtn.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
    }

    fun login(){
        val email = binding.etEmail.text.trim().toString()
        val pass = binding.etPass.text.trim().toString()
        LoadingDialog.displayLoadingWithText(this,"please wait...",false)
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {
            LoadingDialog.hideLoading()
            startActivity(Intent(this,HomeActivity::class.java))
        }.addOnFailureListener { e ->
            LoadingDialog.hideLoading()
            Utility.showToast(this,e.message.toString())
        }

    }
}