package com.veys.countryapplication.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.veys.countryapplication.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>
    // list uuid değerlerini döndürür
    // vararg birden fazla obje alır ve tek tek döndürür. bunun kullanma sebebi internetten kaç adet veri geleceğinin belirsiz olmasıdır.

    @Query("SELECT * FROM country")
    suspend fun getAllCountries():List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}