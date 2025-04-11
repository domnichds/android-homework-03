package com.example.library

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

// Класс с сылками на элементы карточки и методом для бинда
class LibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cardView: CardView = view.findViewById(R.id.cv_library_item)
    val nameTextView: TextView = view.findViewById(R.id.tv_card_item_name)
    val idTextView: TextView = view.findViewById(R.id.tv_card_item_id)
    val iconImageView: ImageView = view.findViewById(R.id.iv_card_icon)

    fun bind(item: LibraryItem) {
        Log.d("LibraryAdapter", "onBindViewHolder: Элемент ${item.id} (${item.name}) перерисован")

        nameTextView.text = item.name
        idTextView.text = "ID: ${item.id}"
        // Установка иконки в зависимости от типа элемента
        iconImageView.setImageResource(
            when (item) {
                is Book -> R.drawable.book
                is Disk -> R.drawable.disk
                is Newspaper -> R.drawable.newspaper
                else -> R.drawable.unknown
            }
        )

        // Привязка слушателя к методу onItemClick
        itemView.setOnClickListener {
            val context = itemView.context
            val intent = DetailActivity.createIntent(context, item)
            context.startActivity(intent)
        }

        // Костыль для автоматической прокрутки длинных названий
        nameTextView.isSelected = true
    }
}