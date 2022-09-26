package com.amvlabs.ryancouncil


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.amvlabs.ryancouncil.activities.CouncilActivity
import com.amvlabs.ryancouncil.activities.StudentActivity
import com.amvlabs.ryancouncil.utils.*
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log


class HomeActivity : AppCompatActivity() {
    lateinit var actionbar: ActionBar
    lateinit var auth: FirebaseAuth
    var user_type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        actionbar = supportActionBar!!
//        actionbar.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        readBundle()
        if (user_type.equals("student", true)) {
            val intent = Intent(this,StudentActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this,CouncilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readBundle() {
        val intent = intent.extras
        user_type = intent?.getString(Constants.USER_TYPE,"").toString()
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
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}