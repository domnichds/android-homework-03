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
class LibraryViewHolder(view: View, private val onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

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
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("ITEM_TYPE", when (item) {
                    is Book -> "Book"
                    is Disk -> "Disk"
                    is Newspaper -> "Newspaper"
                    else -> "Unknown"
                })
                putExtra("ITEM_ICON", when (item) {
                    is Book -> R.drawable.book
                    is Disk -> R.drawable.disk
                    is Newspaper -> R.drawable.newspaper
                    else -> R.drawable.unknown
                })
                putExtra("ITEM_NAME", item.name)
                putExtra("ITEM_ID", item.id)
                when (item) {
                    is Book -> {
                        putExtra("BOOK_AUTHOR", item.author)
                        putExtra("BOOK_NUMBER_OF_PAGES", item.numberOfPages)
                    }
                    is Disk -> {
                        putExtra("DISK_TYPE", if (item.type == 0) "CD" else "DVD")
                    }
                    is Newspaper -> {
                        putExtra("NEWSPAPER_MONTH", item.monthOfPublication.russianName)
                        putExtra("NEWSPAPER_ISSUE_NUMBER", item.issueNumber)
                    }
                    else -> {}
                }
            }
            context.startActivity(intent)
        }

        // Костыль для автоматической прокрутки длинных названий
        nameTextView.isSelected = true
    }
}