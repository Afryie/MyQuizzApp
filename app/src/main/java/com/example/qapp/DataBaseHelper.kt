package com.example.qapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


object TableInfo: BaseColumns {
    const val TABLE_NAME = "Quiz"
    const val TABLE_QUESTION = "Pytanie"
    const val TABLE_A1 = "Odp1"
    const val TABLE_A2 = "Odp2"
    const val TABLE_A3 = "Odp3"
    const val TABLE_A4 = "Odp4"
    const val TABLE_ANSWER ="numer"

}

object BasicCommand {

    const val SQL_CREATE_TABLE = "CREATE TABLE ${TableInfo.TABLE_NAME} (" + "${BaseColumns._ID} INTEGER PRIMARY KEY," +
    "${TableInfo.TABLE_QUESTION}  TEXT NOT NULL," +
            "${TableInfo.TABLE_A1} TEXT NOT NULL," +
            "${TableInfo.TABLE_A2} TEXT NOT NULL," +
            "${TableInfo.TABLE_A3} TEXT NOT NULL," +
            "${TableInfo.TABLE_A4} TEXT NOT NULL," +
            "${TableInfo.TABLE_ANSWER} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
    //const val SQL_COUNT_TABLE = "SELECT COUNT(*) FROM ${TableInfo.TABLE_NAME}"

}


class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.TABLE_NAME, null , 1)
{

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE)
    }

}