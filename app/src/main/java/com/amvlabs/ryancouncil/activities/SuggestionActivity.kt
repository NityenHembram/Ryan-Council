package com.amvlabs.ryancouncil.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.core.graphics.green
import com.amvlabs.ryancouncil.R
import com.amvlabs.ryancouncil.databinding.ActivitySuggestionBinding
import com.amvlabs.ryancouncil.utils.Constants.ACTION
import com.amvlabs.ryancouncil.utils.Constants.KEY_SUB
import com.amvlabs.ryancouncil.utils.Constants.USER_NAME
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SuggestionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuggestionBinding
    val suggestion = ""
    lateinit var db: FirebaseFirestore
    var name = ""
    var sub = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        readIntent()

        binding.subBtn.setOnClickListener { submit() }
    }

    private fun readIntent() {
        sub = intent.extras?.getString(KEY_SUB).toString()
        name = intent.extras?.getString(USER_NAME).toString()
    }

    private fun submit() {
        val radioGp = binding.rbGroup
        val id = radioGp.checkedRadioButtonId
        val radiobtn = findViewById<RadioButton>(id)
        val action = binding.rbGroup.indexOfChild(radiobtn)
        val suggestion = binding.etSuggestion.text.toString()
        val data = mutableMapOf<String, String>()
        if (suggestion.isNotEmpty() && suggestion != null) {
            data[USER_NAME] = name
            data["suggestion"] = suggestion
            data[ACTION] = action.toString()
            db.collection("suggestion").document(name).set(data).addOnSuccessListener {
                db.collection("complains").get().addOnSuccessListener{
                    val doc = it.documents
                    doc.forEach { u ->
                        val userName = u.id.substringBefore("_")
                        Log.d("TAG", "submit: $userName  $name")
                        if(userName == name){
                            u.reference.collection(name).document(sub).update("action",action).addOnSuccessListener {
                                Utility.showToast(baseContext,"Success")
                                finish()
                            }.addOnFailureListener{
                                Log.d("TAG", "submit: ${it.message}")
                                Utility.showToast(baseContext,"${it.message}")
                            }
                        }
                    }
                }
            }
        }


    }

    private fun setData() {
        db.collection("suggestion").document()
    }
}