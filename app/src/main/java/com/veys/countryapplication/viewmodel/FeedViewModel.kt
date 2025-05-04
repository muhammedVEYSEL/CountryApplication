package com.veys.countryapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.convertUUIDToByte
import com.veys.countryapplication.model.Country
import com.veys.countryapplication.services.CountryAPIServices
import com.veys.countryapplication.services.CountryDao
import com.veys.countryapplication.services.CountryDatabase
import com.veys.countryapplication.util.CustomShrededPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiServices = CountryAPIServices()
    private val disposible = CompositeDisposable()
    private var customPreferences = CustomShrededPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>() //Mutable olmasının amacı değiştirilebilirlik özelliği
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime ){
            getDataFromROOM()
        }else{
            getDataFromAPI()
        }
    }
    fun refreshFromAPI(){
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
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // listeyi tekil eleman halde getirmek için kullanılır list -> individual
            var i = 0
            while (i<list.size){
                list[i].uuid = listLong[i].toInt()
                i += 1
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime()) // nanoTime en detaylı zaman dilimini verir
    }

    private fun getDataFromROOM(){
        countryLoading.value = true
        launch {
            val countiries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countiries)
            Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposible.clear()
    }

}