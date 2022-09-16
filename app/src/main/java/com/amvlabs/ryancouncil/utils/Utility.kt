package com.amvlabs.ryancouncil.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

object Utility {
    fun showToast(ctx: Context,message:String){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
    }

    fun isEmailValid(email: String):Boolean{
        val pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}