package com.example.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

// Объявление класса адаптера
class LibraryAdapter() : ListAdapter<LibraryItem, LibraryViewHolder>(LibraryItemDiffCallback()) {

    // Переопределение метода создания элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        // Подтягивание данных из макета и "надувание" их в объект ViewGroup
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        // Возврат созданного экземпляра ViewHolder
        return LibraryViewHolder(view)
    }

    // Переопределение метода отображения элемента на экране
    override fun onBindViewHolder(holder: LibraryViewHolder, index: Int) {
        val item = getItem(index)
        holder.bind(item)
    }
}