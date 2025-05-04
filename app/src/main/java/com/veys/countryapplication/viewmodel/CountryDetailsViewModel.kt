package com.veys.countryapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veys.countryapplication.model.Country
import com.veys.countryapplication.services.CountryDatabase
import kotlinx.coroutines.launch

class CountryDetailsViewModel(application: Application):BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid:Int){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }

}