package com.amvlabs.ryancouncil.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class Preference(val context: Context) {

    // Storing data into SharedPreferences
    lateinit var sharedPreferences: SharedPreferences
    // Creating an Editor object to edit(write to the file)
    lateinit var myEdit:SharedPreferences.Editor
    init{
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        myEdit = sharedPreferences.edit()

    }


    fun putString(key: String, text: String) {
        myEdit.putString(key, text)
        myEdit.commit()
    }

    fun putInt(key: String, text: Int) {
        myEdit.putInt(key, text)
        myEdit.commit()
    }

    fun getString(key: String, text: String):String? {
        return sharedPreferences.getString(key,text)
    }

    fun getInt(key: String, text: Int):Int {
        return sharedPreferences.getInt(key, text)
    }

}