package com.amvlabs.ryancouncil.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.amvlabs.ryancouncil.MainActivity
import com.amvlabs.ryancouncil.R
import com.amvlabs.ryancouncil.databinding.ActivityStudentBinding
import com.amvlabs.ryancouncil.model.ComplainModel
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentActivity : AppCompatActivity() {

    lateinit var binding: ActivityStudentBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    var name:String = ""
    var uid:String = ""
    var complain_count = 0
    val defaultAction = "-1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        val spinner: Spinner =binding.spinner
        binding.etUserName.text = name
        ArrayAdapter.createFromResource(baseContext, R.array.planets_array, android.R.layout.simple_spinner_item
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
        val category = binding.spinner.selectedItem.toString()
        val sub = binding.etSub.text.toString()
        if(uid.isNotEmpty() && name.isNotEmpty()) {
            val complainModel = ComplainModel(name,complain,category,sub,defaultAction)
            db.collection("complains").document("${name}_$uid").collection(name)
                .document(sub).set(complainModel).addOnSuccessListener {
                    binding.etComplain.text?.clear()
                    Utility.showToast(baseContext,"Complain Successfully added.")
                }.addOnFailureListener {
                    Utility.showToast(baseContext,"Some Error Occur")
                }
        }
    }

    private fun readBundle() {
        val userName = Preference(baseContext).getString(Constants.USER_NAME,"")
        val userUid = Preference(baseContext).getString(Constants.UID,"")
        name =userName.toString()
        uid = userUid.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout_btn -> {
                logout()
            }
//            android.R.id.home -> {
//                Utility.showToast(baseContext,"back pressed")
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        auth.signOut()
        Preference(baseContext).myEdit.clear().apply()
        Global.setUserDetails(null)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}