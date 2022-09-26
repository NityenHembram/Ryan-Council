package com.amvlabs.ryancouncil.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.MainActivity
import com.amvlabs.ryancouncil.R
import com.amvlabs.ryancouncil.adapter.OnItemClickListener
import com.amvlabs.ryancouncil.adapter.RecyclerViewAdapter
import com.amvlabs.ryancouncil.databinding.ActivityCouncilBinding
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference
import com.amvlabs.ryancouncil.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CouncilActivity : AppCompatActivity(),OnItemClickListener {
    lateinit var binding: ActivityCouncilBinding
    lateinit var db: FirebaseFirestore
    lateinit var collectionReference: CollectionReference
    lateinit var auth: FirebaseAuth
    var userList = ArrayList<String>()
    lateinit var adapter: RecyclerViewAdapter
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouncilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        collectionReference= db.collection("complains")
        initRV()
        readData()
    }

    private fun initRV() {
        rv = binding.councilRv
        rv.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
    }

    private fun readData() {
        collectionReference.get().addOnSuccessListener{
            it.documents.forEach {doc ->
                val coll = doc.id.substringBefore("_")
                userList.add(coll)
            }
            adapter = RecyclerViewAdapter(userList,this)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Utility.showToast(baseContext,it.message.toString())
        }


    }

    override fun onItemClick(item: String) {
        val intent = Intent(this,ComplainListActivity::class.java)
        intent.putExtra("coll_name",item)
        startActivity(intent)
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