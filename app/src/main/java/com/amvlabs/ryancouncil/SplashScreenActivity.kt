package com.amvlabs.ryancouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.amvlabs.ryancouncil.model.UserDetails
import com.amvlabs.ryancouncil.utils.Constants
import com.amvlabs.ryancouncil.utils.Constants.USER_TYPE
import com.amvlabs.ryancouncil.utils.Global
import com.amvlabs.ryancouncil.utils.Preference

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            val uid = Preference(baseContext).getString(Constants.UID,"")
            val name = Preference(baseContext).getString(Constants.USER_NAME,"")
            val user_type = Preference(baseContext).getString(USER_TYPE,"")
            if(uid?.isNotEmpty() == true && name?.isNotEmpty() == true){
                val intent = Intent(this,HomeActivity::class.java)
                Global.setUserDetails(UserDetails(uid,name,user_type))
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        },2000)
    }
}