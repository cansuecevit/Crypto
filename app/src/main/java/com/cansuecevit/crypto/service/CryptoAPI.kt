package com.cansuecevit.crypto.service

import com.cansuecevit.crypto.model.Crypto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {


    @GET("currencies/ticker?key=f27fb6614e05b9461e1ad904090fbfa0")
    fun getCrypto(): Single<List<Crypto>>
}