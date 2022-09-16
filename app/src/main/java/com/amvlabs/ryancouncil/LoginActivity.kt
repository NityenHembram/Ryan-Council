package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.amvlabs.ryancouncil.databinding.ActivityLoginBinding
import com.amvlabs.ryancouncil.dialog.LoadingDialog
import com.amvlabs.ryancouncil.model.UserDetails
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth:FirebaseAuth
    lateinit var db:FirebaseFirestore
    var uid = ""
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

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
        if(email.isNotEmpty() && Utility.isEmailValid(email)){
            if(pass.isNotEmpty()){
                LoadingDialog.displayLoadingWithText(this,"please wait...",false)
                auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {it->
                    uid = it.user?.uid.toString()
                    Log.d("car", "login: $uid")
                    db.collection("complains").get().addOnSuccessListener { Q->
                        val documents = Q.documents
                        documents.forEach{ doc ->
                            val d = doc.id.substringAfter("_")
                            if(uid == d){
                                name = doc.get("name").toString()
                                Global.setUserDetails(UserDetails(uid,name))
                                val intent = Intent(this,HomeActivity::class.java)
                                intent.putExtra(Constants.USER_NAME,name)
                                LoadingDialog.hideLoading()
                                startActivity(intent)
                                finish()
                            }
                        }
                    }.addOnFailureListener { p ->
                        LoadingDialog.hideLoading()
                        Utility.showToast(this,p.message.toString())
                    }

                }.addOnFailureListener { e ->
                    LoadingDialog.hideLoading()
                    Utility.showToast(this,e.message.toString())
                }
            }else{
                binding.passWarning.text = "Please enter password"
                binding.passWarning.visibility = View.VISIBLE
                if ( binding.emailWarning.isVisible || binding.passWarning.isVisible)  {
                    binding.emailWarning.visibility = View.GONE
                    binding.passWarning.visibility = View.GONE
                }
            }
        }else{
            binding.emailWarning.text = "Please enter valid email"
            binding.emailWarning.visibility = View.VISIBLE
            if (binding.emailWarning.isVisible || binding.passWarning.isVisible) {
                binding.emailWarning.visibility = View.GONE
                binding.passWarning.visibility = View.GONE
            }
        }


    }
}