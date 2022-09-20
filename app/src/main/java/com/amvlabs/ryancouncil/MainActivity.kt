package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amvlabs.ryancouncil.databinding.ActivityMainBinding
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Constants.USER_TYPE
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetails = Global.getUserDetails()
        if(userDetails != null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        binding.studentBtn.setOnClickListener {
            Preference(baseContext).putString(Constants.USER_TYPE,"student")
            val intent = Intent(this,LoginActivity::class.java)
            intent.putExtra(USER_TYPE,"student")
            startActivity(intent)
            finish()
        }

        binding.councilBtn.setOnClickListener {
            Preference(baseContext).putString(Constants.USER_TYPE,"council")
            val intent = Intent(this,LoginActivity::class.java)
            intent.putExtra(USER_TYPE,"council")
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}