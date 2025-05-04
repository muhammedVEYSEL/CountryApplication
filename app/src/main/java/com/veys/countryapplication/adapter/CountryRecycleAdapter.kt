package com.veys.countryapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.veys.countryapplication.R
import com.veys.countryapplication.databinding.ItemCountryRowBinding
import com.veys.countryapplication.model.Country
import com.veys.countryapplication.util.dowloadFromUrl
import com.veys.countryapplication.util.placeHolderProgressBar
import com.veys.countryapplication.view.FeedFragmentDirections

class CountryRecycleAdapter(private val countryList : ArrayList<Country>) :RecyclerView.Adapter<CountryRecycleAdapter.CountryHolder>(){
    class CountryHolder(val binding : ItemCountryRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        //val binding = ItemCountryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding = DataBindingUtil.inflate<ItemCountryRowBinding>(LayoutInflater.from(parent.context),
            R.layout.item_country_row,parent,false)
        return CountryHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        holder.binding.country = countryList[position]
        /*
        holder.binding.counrtyNameText.text = countryList[position].countryName
        holder.binding.contryDetailsText.text = countryList[position].countryCapital

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.Countryimageview.dowloadFromUrl(countryList[position].countryImageUri,
            placeHolderProgressBar(holder.itemView.context)
        )*/


    }

    override fun getItemCount(): Int {
       return countryList.size
    }
    //swipe refresh layout güncellendiği zaman country list de güncellenmesi gerekir
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}