package com.cansuecevit.crypto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cansuecevit.crypto.R
import com.cansuecevit.crypto.databinding.ItemCryptoBinding
import com.cansuecevit.crypto.model.Crypto
import com.cansuecevit.crypto.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_crypto.view.*
import java.util.ArrayList

class CryptoAdapter(val cryptoList: ArrayList<Crypto>): RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>(),CryptoClickListener {

    class CryptoViewHolder(var view: ItemCryptoBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCryptoBinding>(inflater, R.layout.item_crypto,parent,false)
        return CryptoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {

        holder.view.crypto=cryptoList[position]
        holder.view.listener=this

    }

    fun updateCountryList(newCountryList:List<Crypto>){
        cryptoList.clear()
        cryptoList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCryptoClicked(v: View) {
        val id=v.cryptoId.text.toString().toInt()
        val action=HomeFragmentDirections.actionHomeFragmentToCryptoFragment(id)
        Navigation.findNavController(v).navigate(action)
    }
}