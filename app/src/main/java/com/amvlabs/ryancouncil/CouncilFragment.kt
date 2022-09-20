package com.amvlabs.ryancouncil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.adapter.OnItemClickListener
import com.amvlabs.ryancouncil.adapter.RecyclerViewAdapter
import com.amvlabs.ryancouncil.databinding.FragmentCouncilBinding
import com.amvlabs.ryancouncil.utils.UiHandler
import com.amvlabs.ryancouncil.utils.Utility
import com.google.android.gms.common.util.DataUtils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CouncilFragment : Fragment(),OnItemClickListener {

    lateinit var binding:FragmentCouncilBinding
    lateinit var db:FirebaseFirestore
    lateinit var collectionReference:CollectionReference
    var userList = ArrayList<String>()
    lateinit var adapter:RecyclerViewAdapter
    lateinit var rv:RecyclerView
    var uiHandler:UiHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCouncilBinding.inflate(inflater,container,false)
        db = Firebase.firestore
        collectionReference= db.collection("complains")
        initRV()
        readData()
        return binding.root
    }

    private fun initRV() {
        rv = binding.councilRv
        rv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

    private fun readData() {
        collectionReference.get().addOnSuccessListener {
            it.documents.forEach {doc ->
                val coll = doc.id.substringBefore("_")
                userList.add(coll)
            }
            adapter = RecyclerViewAdapter(userList,this)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Utility.showToast(requireContext(),it.message.toString())
        }


    }

    override fun onItemClick(item: String) {
        uiHandler?.openComplainListFragment(item)
        val manager = activity?.supportFragmentManager?.beginTransaction()
        val complainListFragment = ComplainListFragment()
        val bundle = Bundle()
        bundle.putString("name",item)
        complainListFragment.arguments = bundle
        manager?.replace(R.id.fragment_container, complainListFragment)
        manager?.commit()
    }

}