package com.example.library

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

interface  OnItemClickListener {
    fun onItemClick(id: Int)
}

// Объявление класса адаптера. Поле - список предметов библиотеки
class LibraryAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<LibraryItem, LibraryAdapter.ViewHolder>(LibraryItemDiffCallback()) {

    // Константы для изменения состояния карточки
    companion object {
        val ENABLE_ELEVATION = R.dimen.enable_elevation
        val DISABLE_ELEVATION = R.dimen.disable_elevation
        const val ENABLE_ALPHA = 1f
        const val DISABLE_ALPHA = 0.3f
    }

    // Внутренний класс с сылками на элементы карточки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cv_library_item)
        val nameTextView: TextView = view.findViewById(R.id.tv_card_item_name)
        val idTextView: TextView = view.findViewById(R.id.tv_card_item_id)
        val iconImageView: ImageView = view.findViewById(R.id.iv_card_icon)
    }

    // Переопределение метода создания элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Подтягивание данных из макета и "надувание" их в объект ViewGroup
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        // Возврат созданного экземпляра ViewHolder
        return ViewHolder(view)
    }

    // Переопределение метода отображения элемента на экране
    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        // Подтягивание данных из списка и установка их в элементы карточки
        val item = getItem(index)

        Log.d("LibraryAdapter", "onBindViewHolder: Элемент ${item.id} (${item.name}) перерисован")

        holder.nameTextView.text = item.name
        holder.idTextView.text = "ID: ${item.id.toString()}"
        // Установка иконки в зависимости от типа элемента
        holder.iconImageView.setImageResource(
            when (item) {
                is Book -> R.drawable.book
                is Disk -> R.drawable.disk
                is Newspaper -> R.drawable.newspaper
                else -> R.drawable.unknown
            }
        )

        // Установка стиля в зависимости от доступности
        if (item.accessible) {
            holder.cardView.elevation =
                holder.itemView.context.resources.getDimension(ENABLE_ELEVATION)
            holder.nameTextView.alpha = ENABLE_ALPHA
            holder.idTextView.alpha = ENABLE_ALPHA
            holder.iconImageView.alpha = ENABLE_ALPHA
        } else {
            holder.cardView.elevation =
                holder.itemView.context.resources.getDimension(DISABLE_ELEVATION)
            holder.nameTextView.alpha = DISABLE_ALPHA
            holder.idTextView.alpha = DISABLE_ALPHA
            holder.iconImageView.alpha = DISABLE_ALPHA
        }

        // Привязка слушателя к методу onItemClick
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item.id)
        }
    }
}