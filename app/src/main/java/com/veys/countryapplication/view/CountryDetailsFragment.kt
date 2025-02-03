package com.veys.countryapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.veys.countryapplication.view.CountryDetailsFragmentArgs
import com.veys.countryapplication.databinding.FragmentCountryDetailsBinding
import com.veys.countryapplication.viewmodel.CountryDetailsViewModel


class CountryDetailsFragment : Fragment() {
    private var _binding : FragmentCountryDetailsBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel : CountryDetailsViewModel
    //private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CountryDetailsViewModel::class.java]
        viewModel.getDataFromRoom()

        observeLiveData()

        /*arguments?.let {
            countryUuid = CountryDetailsFragmentArgs.fromBundle(it).countryUuid
        }*/
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryCurrency.text = it.countryCurrency
                binding.countryRegion.text = it.countryRegion
                binding.countryLanguage.text = it.countryLanguage

            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}