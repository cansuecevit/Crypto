package com.cansuecevit.crypto.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cansuecevit.crypto.model.Crypto

@Database(entities = arrayOf(Crypto::class),version = 1)

abstract  class CryptoDatabase: RoomDatabase() {
    abstract fun cryptoDao():CryptoDAO
    companion object {

        @Volatile private var instance : CryptoDatabase?=null

        private val lock = Any()
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it
            }
        }
        private fun makeDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,CryptoDatabase::class.java,"cryptodatabase").build()
    }

}