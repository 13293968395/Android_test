package com.example.newsshow.logic.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class AccountDatabase(val context: Context, name: String, version: Int): SQLiteOpenHelper(context,name,null,version) {

    private val createAccount = "create table Account (" +
            "id integer primary key autoincrement," +
            "userName text,"+
            "account text," +
            "password integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createAccount)
        Toast.makeText(context,"create success", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}