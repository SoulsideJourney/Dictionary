package ru.soulsidejourney.dictionary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pair (
    var id: String?,
    var firstWord: String,
    var secondWord: String): Parcelable
