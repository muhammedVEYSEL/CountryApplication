package com.veys.countryapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veys.countryapplication.model.Country
import com.veys.countryapplication.services.CountryAPIServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val countryApiServices = CountryAPIServices()
    private val disposible = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>() //Mutable olmasının amacı değiştirilebilirlik özelliği
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromAPI()

    }

    private fun getDataFromAPI(){
        countryLoading.value = true

        disposible.add(
            countryApiServices.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList : List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list : List<Country>){

    }

    private fun getDataFromROOM(){

    }

}