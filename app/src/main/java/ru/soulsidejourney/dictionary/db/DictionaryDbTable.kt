package ru.soulsidejourney.dictionary.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import ru.soulsidejourney.dictionary.data.Pair
import ru.soulsidejourney.dictionary.db.PairEntry.FIRST_WORD_COL
import ru.soulsidejourney.dictionary.db.PairEntry.SECOND_WORD_COL
import ru.soulsidejourney.dictionary.db.PairEntry._ID
import ru.soulsidejourney.dictionary.db.PairEntry.TABLE_NAME

class DictionaryDbTable(context: Context) {
    private val TAG = DictionaryDbTable::class.java.simpleName

    private val dbHelper = DictionaryDb(context)

    fun store(pair: Pair): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values){
            put(FIRST_WORD_COL, pair.firstWord)
            put(SECOND_WORD_COL, pair.secondWord)
        }

        val id = db.transaction {
            insert(TABLE_NAME, null, values)
        }

        Log.d(TAG, "Новая пара слов записана в БД ${pair}")

        return id
    }

    fun edit(pair: Pair) : Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values){
            put(FIRST_WORD_COL, pair.firstWord)
            put(SECOND_WORD_COL, pair.secondWord)
        }

        val id = db.transaction {
            update(TABLE_NAME, values, "id = ${pair.id}", null)
        }

        Log.d(TAG, "Пара слов перезаписана в БД ${pair}")

        return id
    }

    fun delete (id: String) : Int {
        val db = dbHelper.writableDatabase

        val id = db.transaction {
            delete(TABLE_NAME, "id = ${id}", null)
        }

        Log.d(TAG, "Пара слов удалена из БД")

        return id
    }

    fun readAllPairs(order: String): List<Pair> {
        val columns = arrayOf(_ID, FIRST_WORD_COL, SECOND_WORD_COL)
        //val order = "$_ID ASC"

        val db = dbHelper.readableDatabase

        val cursor = db.doQuery(TABLE_NAME, columns, orderBy = order)

        return parsePairsFromCursor(cursor)
    }

    private fun parsePairsFromCursor(cursor: Cursor): MutableList<Pair> {
        val pairs = mutableListOf<Pair>()
        while (cursor.moveToNext()) {
            val id = cursor.getString(_ID)
            val first = cursor.getString(FIRST_WORD_COL)
            val second = cursor.getString(SECOND_WORD_COL)
            pairs.add(Pair(id, first, second))
        }
        cursor.close()
        return pairs
    }
}

private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                                        selectionArgs: Array<String>? = null, groupBy: String? = null,
                                        having: String? = null, orderBy: String? = null): Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

@SuppressLint("Range")
private fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))

private inline fun <T> SQLiteDatabase.transaction (function: SQLiteDatabase.() -> T) : T {
    beginTransaction()
    val result = try {
        val returnValue = function()
        setTransactionSuccessful()
        returnValue
    } finally {
        endTransaction()
    }
    close()

    return result
}