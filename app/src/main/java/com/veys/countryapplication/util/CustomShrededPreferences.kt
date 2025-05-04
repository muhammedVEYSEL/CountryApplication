package com.veys.countryapplication.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomShrededPreferences {

    companion object{

        private const val PREFERENCES_TIME = "preferences_time"
        private var sheredPreferences : SharedPreferences? = null

        @Volatile private var instance: CustomShrededPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): CustomShrededPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomShrededPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomShrededPreferences(context: Context) : CustomShrededPreferences{
            sheredPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomShrededPreferences()
        }
    }

    fun saveTime(time: Long){

        sheredPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun getTime() = sheredPreferences?.getLong(PREFERENCES_TIME,0)
}