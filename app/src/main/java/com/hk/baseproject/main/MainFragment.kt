package com.hk.baseproject.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hk.baseproject.R
import com.hk.baseproject.adapter.CountryListAdapter
import com.hk.baseproject.databinding.FragmentMainBinding
import com.hk.baseproject.di.MyApplication
import javax.inject.Inject


class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private var _binding: FragmentMainBinding? = null

    private val countriesAdapter= CountryListAdapter(arrayListOf())
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.mainComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countriesList.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=countriesAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
            viewModel.refresh()
        }
        viewModel.loadCountry()
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.items.observe(viewLifecycleOwner, Observer {countries->
            countries?.let {
                binding.countriesList.visibility=View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {isLoading->
            isLoading?.let {
                binding.loadingView.visibility=if(it)View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility=View.GONE
                    binding.countriesList.visibility=View.GONE
                }
            }
        })
        viewModel.isDataLoadingError.observe(viewLifecycleOwner,{isDataLoadingError->
            isDataLoadingError?.let { binding.listError.visibility=if(it) View.VISIBLE else View.GONE }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}