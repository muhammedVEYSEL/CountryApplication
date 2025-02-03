package com.veys.countryapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veys.countryapplication.adapter.CountryRecycleAdapter
import com.veys.countryapplication.databinding.FragmentFeedBinding
import com.veys.countryapplication.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryRecycleAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]//model ve görünüm birbirine bağlandı
        viewModel.refreshData()

        binding.recyclerViewCountryList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCountryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerViewCountryList.visibility = View.GONE
            binding.contryErrorText.visibility = View.GONE
            binding.countryLoadingProgress.isVisible = true //KTX kullanarak yazım şekli
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }
    //verilerin gelip gelmediğini gözlemle
    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerViewCountryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it){
                    binding.contryErrorText.visibility = View.VISIBLE
                }else{
                    binding.contryErrorText.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it) {
                    binding.countryLoadingProgress.visibility = View.VISIBLE
                    binding.recyclerViewCountryList.visibility = View.GONE
                    binding.contryErrorText.visibility = View.GONE
                }else{
                    binding.countryLoadingProgress.visibility = View.GONE
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}