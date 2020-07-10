package com.cansuecevit.crypto.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cansuecevit.crypto.model.Crypto
import com.cansuecevit.crypto.service.CryptoDatabase
import kotlinx.coroutines.launch

class CryptoViewModel (application: Application):BaseViewModel(application) {


    val cryptoLiveData= MutableLiveData<Crypto>()

    fun getDataFromRoom(uuid:Int){
        launch {
            val dao =CryptoDatabase(getApplication()).cryptoDao()
            val crypto =dao.getCrypto(uuid)
            cryptoLiveData.value=crypto

        }
    }

}