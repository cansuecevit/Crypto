package com.cansuecevit.crypto.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cansuecevit.crypto.model.Crypto

@Dao
interface CryptoDAO {

    @Insert
    suspend fun insertAll(vararg crypto :Crypto):List<Long>

    @Query("SELECT * FROM crypto")
    suspend fun getAllCrypto():List<Crypto>

    @Query("SELECT*FROM crypto WHERE uuid=:cryptoId")
    suspend fun getCrypto(cryptoId:Int):Crypto

    @Query("DELETE FROM crypto")
    suspend fun deleteAllCrypto()
}