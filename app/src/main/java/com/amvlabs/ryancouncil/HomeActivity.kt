package com.amvlabs.ryancouncil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val studentFragment = StudentFragment()
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(R.id.fragment_container,studentFragment)
        manager.commit()
    }
}