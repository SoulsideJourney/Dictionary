package ru.soulsidejourney.dictionary.db

import android.provider.BaseColumns

val DATABASE_NAME = "dictionary.db"
val DATABASE_VERSION = 10

object PairEntry : BaseColumns {
    val TABLE_NAME = "pair"
    val _ID = "id"
    val FIRST_WORD_COL = "first_word"
    val SECOND_WORD_COL = "second_word"
}