package com.fitz.minhascores.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context) : SQLiteOpenHelper(context, "person.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table colors(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, code int)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table colors")
        this.onCreate(db)
    }
}
