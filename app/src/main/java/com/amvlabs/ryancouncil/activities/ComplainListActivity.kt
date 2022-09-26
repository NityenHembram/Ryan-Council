package com.amvlabs.ryancouncil.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.adapter.ComplainListAdapter
import com.amvlabs.ryancouncil.adapter.OnComplainClickListener
import com.amvlabs.ryancouncil.databinding.ActivityComplainListBinding
import com.amvlabs.ryancouncil.model.ComplainModel
import com.amvlabs.ryancouncil.utils.Constants.ACTION
import com.amvlabs.ryancouncil.utils.Constants.KEY_CATEGORY
import com.amvlabs.ryancouncil.utils.Constants.KEY_COMPLAIN
import com.amvlabs.ryancouncil.utils.Constants.KEY_SUB
import com.amvlabs.ryancouncil.utils.Constants.USER_NAME
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplainListActivity : AppCompatActivity(),OnComplainClickListener{
    lateinit var binding: ActivityComplainListBinding
    lateinit var db: FirebaseFirestore
    lateinit var collectionReference: CollectionReference
    val user = ""
    var collection = ""
    val comList = ArrayList<ComplainModel>()
    val defaultAction = "-1"
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        collectionReference = db.collection("complains")
        initRecyclerView()
        readData()
        readBundle()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        recyclerView = binding.comRv
        recyclerView.layoutManager = LinearLayoutManager(baseContext,
            LinearLayoutManager.VERTICAL,false)
    }

    private fun readBundle() {
        collection = intent.extras?.getString("coll_name").toString()
    }

    private fun readData() {
        collectionReference.get().addOnSuccessListener {
            it.documents.forEach { doc ->
                Log.d("TAG", "${doc}")
                doc.reference.collection(collection).get().addOnSuccessListener { k ->

                    k.documents.forEach { l ->
                        Log.d("TAG", "$l")
                        val comModel = ComplainModel(l.getString("name").toString(),l.getString("complain")
                            .toString(),l.getString("cate gory").toString(),l.getString("sub"),l.getString("action"))
                        comList.add(comModel)
                    }
                    recyclerView.adapter = ComplainListAdapter(comList,this)
                }
            }
        }
    }

    override fun onClicked(item:ComplainModel) {
        val intent = Intent(this,ComplainDetailActivity::class.java)
        intent.putExtra(USER_NAME,item.name)
        intent.putExtra(KEY_COMPLAIN,item.complain)
        intent.putExtra(KEY_CATEGORY,item.category)
        intent.putExtra(KEY_SUB,item.sub)
        intent.putExtra(ACTION,item.action)
        startActivity(intent)
    }

}