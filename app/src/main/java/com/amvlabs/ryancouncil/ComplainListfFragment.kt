package com.amvlabs.ryancouncil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.adapter.ComplainListAdapter
import com.amvlabs.ryancouncil.databinding.FragmentComplainListBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ComplainListFragment : Fragment() {

    lateinit var binding: FragmentComplainListBinding
    lateinit var db: FirebaseFirestore
    lateinit var collectionReference: CollectionReference
    val user = ""
    var collection = ""
    val comList = ArrayList<String>()
    lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComplainListBinding.inflate(inflater, container, false)
        db = Firebase.firestore
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        collectionReference = db.collection("complains")
        initRecyclerView()
        readData()
        readBundle()
        return binding.root
    }

    private fun initRecyclerView() {
        recyclerView = binding.comRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

    private fun readBundle() {
        collection = arguments?.getString("name").toString()
    }

    private fun readData() {
        collectionReference.get().addOnSuccessListener {
            it.documents.forEach { doc ->
                doc.reference.collection(collection).get().addOnSuccessListener { k ->
                    k.documents.forEach { l ->
                        comList.add(l.id)
                    }
                    recyclerView.adapter = ComplainListAdapter(comList)
                }
            }
        }
    }




}