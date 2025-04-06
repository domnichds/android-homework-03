package com.example.library

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

// Класс с сылками на элементы карточки и методом для бинда
class LibraryViewHolder(view: View, private val onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
    // Константы для изменения состояния карточки
    companion object {
        val ENABLE_ELEVATION = R.dimen.enable_elevation
        val DISABLE_ELEVATION = R.dimen.disable_elevation
        const val ENABLE_ALPHA = 1f
        const val DISABLE_ALPHA = 0.3f
    }

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

        // Установка стиля в зависимости от доступности
        if (item.accessible) {
            cardView.elevation =
                itemView.context.resources.getDimension(ENABLE_ELEVATION)
            nameTextView.alpha = ENABLE_ALPHA
            idTextView.alpha = ENABLE_ALPHA
            iconImageView.alpha = ENABLE_ALPHA
        } else {
            cardView.elevation =
                itemView.context.resources.getDimension(DISABLE_ELEVATION)
            nameTextView.alpha = DISABLE_ALPHA
            idTextView.alpha = DISABLE_ALPHA
            iconImageView.alpha = DISABLE_ALPHA
        }

        // Привязка слушателя к методу onItemClick
        itemView.setOnClickListener {
            onItemClick(item.id)
        }
    }
}