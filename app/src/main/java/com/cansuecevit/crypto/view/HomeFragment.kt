package com.cansuecevit.crypto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.cansuecevit.crypto.R
import com.cansuecevit.crypto.adapter.CryptoAdapter
import com.cansuecevit.crypto.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel:HomeViewModel
    private val cryptoAdapter= CryptoAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.refreshData()

        cryptoList.layoutManager= LinearLayoutManager(context)
        cryptoList.adapter=cryptoAdapter
        swipeRefreshLayout.setOnRefreshListener {
            cryptoList.visibility=View.GONE
            cryptoError.visibility=View.GONE
            cryptoLoading.visibility=View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing=false
        }
        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.crypto.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                cryptoList.visibility=View.VISIBLE
                cryptoAdapter.updateCountryList(countries)
            }
        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if(error){
                    cryptoError.visibility=View.VISIBLE
                    cryptoList.visibility=View.GONE
                    cryptoLoading.visibility=View.GONE
                }
                else{
                    cryptoError.visibility=View.GONE
                }
            }
        })
        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if(loading){
                    cryptoLoading.visibility=View.VISIBLE
                    cryptoList.visibility=View.GONE
                    cryptoError.visibility=View.GONE
                } else {
                    cryptoLoading.visibility=View.GONE
                }

            }
        })
    }


}
