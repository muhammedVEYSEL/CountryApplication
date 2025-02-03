package com.veys.countryapplication.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.veys.countryapplication.model.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase(){

    // oluşturulan veritabanı için tek bir obje oluşturulmalıdır bunun sebebi
    // eğer veritabanına farklı zamanlardan ya da farklı threadlerdan erişilirse conflict olayı yaşanmasıdır
    // bundan dolayı Singleton mantığı kullanılır.
    abstract fun countryDao() : CountryDao

    companion object {

       @Volatile private var instance : CountryDatabase? = null // bir değişken Volatile tanımlandığı  zaman diğer Thread ler için de görünür hale gelir

       private val Lock = Any()

       operator fun invoke(context : Context) = instance ?: synchronized(Lock){
           instance ?: makeDatabase(context).also {
               instance = it
           }
       }

       private fun makeDatabase(context : Context) = Room.databaseBuilder(
           context.applicationContext,CountryDatabase::class.java,"CountryDatabase"
       ).build()


    }
}