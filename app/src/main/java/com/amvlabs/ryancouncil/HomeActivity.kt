package com.amvlabs.ryancouncil


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference
import com.amvlabs.ryancouncil.utils.UiHandler
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity(), UiHandler {
    lateinit var actionbar: ActionBar
    lateinit var auth: FirebaseAuth
    val user_type = ""
    lateinit var manager: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        actionbar = supportActionBar!!
        auth = FirebaseAuth.getInstance()
        val councilFragment = CouncilFragment()
        councilFragment.uiHandler = this
        manager = supportFragmentManager.beginTransaction()
        if (user_type.equals("student", true)) {
            val studentFragment = StudentFragment()
            manager.replace(R.id.fragment_container, studentFragment)
            manager.commit()
        } else {
            val councilFragment = CouncilFragment()
            manager.replace(R.id.fragment_container, councilFragment)
            manager.commit()
        }
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        Preference(baseContext).myEdit.clear().apply()
        Global.setUserDetails(null)
        finish()
    }

    override fun openComplainListFragment(item: String) {
        val complainListFragment = ComplainListFragment()
        val bundle = Bundle()
        bundle.putString("name",item)
        complainListFragment.arguments = bundle
        manager.replace(R.id.fragment_container, complainListFragment)
        manager.commit()
    }
}