package com.example.library.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Создание Parcelable data-класса для хранения подробной информации об элементе библиотеки
@Parcelize
data class DetailData(
    val itemId: Int,
    val itemType: String,
    val itemName: String,
    val author: String?,
    val pages: Int,
    val diskType: String?,
    val issueNumber: Int,
    val month: String?
) : Parcelable