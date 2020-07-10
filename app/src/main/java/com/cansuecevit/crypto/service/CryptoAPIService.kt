package com.cansuecevit.crypto.service

import com.cansuecevit.crypto.model.Crypto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoAPIService {

    private val BASE_URL ="https://api.nomics.com/v1/"
    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)
    fun getData(): Single<List<Crypto>> {
        return api.getCrypto()
    }
}