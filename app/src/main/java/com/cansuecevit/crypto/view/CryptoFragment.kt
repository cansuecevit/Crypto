package com.cansuecevit.crypto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.cansuecevit.crypto.R
import com.cansuecevit.crypto.databinding.FragmentCryptoBinding
import com.cansuecevit.crypto.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.item_crypto.*

class CryptoFragment : Fragment() {


    private lateinit var viewModel: CryptoViewModel

    private var cryptoId=0
    private lateinit var dataBinding :FragmentCryptoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_crypto,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cryptoId= CryptoFragmentArgs.fromBundle(it).cryptoId
        }
        viewModel= ViewModelProviders.of(this).get(CryptoViewModel::class.java)
        viewModel.getDataFromRoom(cryptoId)

        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.cryptoLiveData.observe(viewLifecycleOwner, Observer { crypto->
            crypto?.let {
                dataBinding.selectedCrypto=crypto
            }
        })
    }
}
