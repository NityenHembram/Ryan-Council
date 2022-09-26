package com.amvlabs.ryancouncil.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amvlabs.ryancouncil.R
import com.amvlabs.ryancouncil.databinding.ActivityComplainDetailBinding
import com.amvlabs.ryancouncil.utils.Constants.ACTION
import com.amvlabs.ryancouncil.utils.Constants.KEY_CATEGORY
import com.amvlabs.ryancouncil.utils.Constants.KEY_COMPLAIN
import com.amvlabs.ryancouncil.utils.Constants.KEY_SUB
import com.amvlabs.ryancouncil.utils.Constants.USER_NAME

class ComplainDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityComplainDetailBinding
    var name = ""
    var complain = ""
    var category = ""
    var sub = ""
    var action = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readIntent()
        setData()

        binding.sugBtn.setOnClickListener{
            val intent = Intent(this,SuggestionActivity::class.java)
            intent.putExtra(USER_NAME,name)
            intent.putExtra(KEY_SUB,sub)
            intent.putExtra(ACTION,action)
            startActivity(intent)
        }
    }

    private fun setData() {
        binding.userTvName.text = name
        binding.comTv.text = complain
        binding.categoryTv.text = category
    }

    private fun readIntent() {
        val extra = intent.extras
        name = extra?.getString(USER_NAME).toString()
        complain = extra?.getString(KEY_COMPLAIN).toString()
        category = extra?.getString(KEY_CATEGORY).toString()
        sub = extra?.getString(KEY_SUB).toString()
        action = extra?.getString(ACTION).toString()
    }
}