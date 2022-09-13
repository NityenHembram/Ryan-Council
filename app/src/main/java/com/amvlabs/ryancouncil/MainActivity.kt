package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amvlabs.ryancouncil.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studentBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.councilBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}