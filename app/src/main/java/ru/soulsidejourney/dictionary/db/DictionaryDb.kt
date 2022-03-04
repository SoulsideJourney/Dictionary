package ru.soulsidejourney.dictionary.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.soulsidejourney.dictionary.db.DATABASE_NAME
import ru.soulsidejourney.dictionary.db.DATABASE_VERSION
import ru.soulsidejourney.dictionary.db.PairEntry

class DictionaryDb(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${PairEntry.TABLE_NAME} (" +
            "${PairEntry._ID} INTEGER PRIMARY KEY," +
            "${PairEntry.FIRST_WORD_COL} TEXT," +
            "${PairEntry.SECOND_WORD_COL} TEXT" +
            ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${PairEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}