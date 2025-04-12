package com.example.library

import androidx.recyclerview.widget.DiffUtil

class LibraryItemDiffCallback : DiffUtil.ItemCallback<LibraryItem>() {
    // Метод для сравнения уникальных идентификаторов объектов
    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean =
        oldItem.id == newItem.id

    // Метод для сравнения содержимого объектов
    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean =
        oldItem == newItem
}