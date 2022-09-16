package com.amvlabs.ryancouncil.utils

import android.util.Patterns
import com.amvlabs.ryancouncil.model.UserDetails

object Global {
    private  var userDetails:UserDetails? = null

   fun setUserDetails(userDetails: UserDetails){
       this.userDetails = UserDetails(userDetails.uid,userDetails.name)
   }
    fun getUserDetails():UserDetails?{
        return userDetails
    }
}