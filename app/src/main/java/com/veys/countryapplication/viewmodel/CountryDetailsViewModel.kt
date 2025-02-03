package com.veys.countryapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veys.countryapplication.model.Country

class CountryDetailsViewModel:ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey","Asia","TRY","Turkish","www.ss.com","Ankara")
        countryLiveData.value = country
    }

}