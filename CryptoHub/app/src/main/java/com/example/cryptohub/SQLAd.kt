package com.example.cryptohub

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLAd (context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table usuario (user text primary key, clave text, bitcoin integer, ethereum integer, ripple integer, litecoin integer, saldo integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists usuario")
        onCreate(db)
    }
    fun deleteDatabase(context: Context): Boolean{
        return context.deleteDatabase("Cryptos")
    }
}