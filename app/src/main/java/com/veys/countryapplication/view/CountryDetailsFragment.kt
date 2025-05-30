package com.veys.countryapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.veys.countryapplication.R
import com.veys.countryapplication.view.CountryDetailsFragmentArgs
import com.veys.countryapplication.databinding.FragmentCountryDetailsBinding
import com.veys.countryapplication.util.dowloadFromUrl
import com.veys.countryapplication.util.placeHolderProgressBar
import com.veys.countryapplication.viewmodel.CountryDetailsViewModel


class CountryDetailsFragment : Fragment() {
    private var _binding : FragmentCountryDetailsBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel : CountryDetailsViewModel
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //_binding = FragmentCountryDetailsBinding.inflate(inflater,container,false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_details,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
                    countryUuid = CountryDetailsFragmentArgs.fromBundle(it).countryUuid
                }
        viewModel = ViewModelProvider(this)[CountryDetailsViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()


    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {

                binding.selectedCountry = country

                /*
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryCurrency.text = country.countryCurrency
                binding.countryRegion.text = country.countryRegion
                binding.countryLanguage.text = country.countryLanguage
                context?.let {
                    binding.countryFlagImage.dowloadFromUrl(country.countryImageUri,
                        placeHolderProgressBar(it)
                    )
                }*/


            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}