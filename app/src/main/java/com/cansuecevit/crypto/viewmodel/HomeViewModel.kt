package com.cansuecevit.crypto.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cansuecevit.crypto.model.Crypto
import com.cansuecevit.crypto.service.CryptoAPIService
import com.cansuecevit.crypto.service.CryptoDatabase
import com.cansuecevit.crypto.utill.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : BaseViewModel(application) {
    private val cryptoAPIService= CryptoAPIService()
    private val disposable= CompositeDisposable()
    private var customPreference= CustomSharedPreferences(getApplication())
    private var refreshTime=30*1000*1000*1000L
    val crypto = MutableLiveData<List<Crypto>>()
    val cryptoError= MutableLiveData<Boolean>()
    val cryptoLoading= MutableLiveData<Boolean>()

    fun refreshData(){

        val updateTime = customPreference.getTime()
        if(updateTime!=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }else
        {
            getDataFromAPI()
        }
    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }
    private fun getDataFromSQLite(){
        launch {
            val crypto=CryptoDatabase(getApplication()).cryptoDao().getAllCrypto()
            showCrypto(crypto)
        }
    }
    private fun getDataFromAPI(){
        cryptoLoading.value=true
        disposable.add(
            cryptoAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Crypto>>(){
                    override fun onSuccess(t: List<Crypto>) {
                        storeSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        cryptoLoading.value=false
                        cryptoError.value=true
                        e.printStackTrace()
                        println(e)
                    }

                })
        )

    }

    private fun showCrypto(cryptoList:List<Crypto>){
        crypto.value=cryptoList
        cryptoError.value=false
        cryptoLoading.value=false
    }
    private fun storeSQLite(list:List<Crypto>){
        cryptoLoading.value=true
        launch {
            val dao =CryptoDatabase(getApplication()).cryptoDao()
            dao.deleteAllCrypto()
            val listLong= dao.insertAll(*list.toTypedArray())
            var i=0
            while (i<list.size)
            {
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }
            showCrypto(list)
        }
        customPreference.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}