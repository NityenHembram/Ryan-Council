package com.amvlabs.ryancouncil.utils

import android.content.Context
import android.widget.Toast

object Utility {
    fun showToast(ctx: Context,message:String){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
    }
}