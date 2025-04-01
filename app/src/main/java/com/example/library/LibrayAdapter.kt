package com.example.library

import android.content.Context
import android.icu.text.Transliterator.Position
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

interface  OnItemClickListener {
    fun onItemClick(item: LibraryItem)
}

// Объявление класса адаптера. Поле - список предметов библиотеки
class LibraryAdapter(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) : ListAdapter<LibraryItem, LibraryAdapter.ViewHolder>(LibraryItemDiffCallback()) {
    // Внутренний класс с сылками на элементы карточки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val idTextView: TextView = view.findViewById(R.id.idTextView)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
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
                holder.itemView.context.resources.getDimension(R.dimen.enable_elevation)
            holder.nameTextView.alpha = 1f
            holder.idTextView.alpha = 1f
            holder.iconImageView.alpha = 1f
        } else {
            holder.cardView.elevation =
                holder.itemView.context.resources.getDimension(R.dimen.disable_elevation)
            holder.nameTextView.alpha = 0.3f
            holder.idTextView.alpha = 0.3f
            holder.iconImageView.alpha = 0.3f
        }

        // Привязка слушателя к методу onItemClick
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                toggleItemState(position)
                itemClickListener.onItemClick(getItem(position))
            }
        }
    }

    fun updateList(newList: List<LibraryItem>) {
        submitList(newList)
    }

    // Метод для удаления элемента по позиции
    fun removeItem(position: Int) {
        // Создание изменяемой копии текущего списка
        val currentListMutable = currentList.toMutableList()
        // Вывод информационного тоста
        Toast.makeText(context, "Элемент с id ${currentListMutable[position].id} удален", Toast.LENGTH_SHORT).show()
        // Удаление элемента по позиции
        currentListMutable.removeAt(position)
        // Передача нового списка адаптеру
        submitList(currentListMutable)
    }

    fun toggleItemState(position: Int) {
        // Получение старого элемента и измененного с другим состоянием
        val item = getItem(position)
        val updatedItem = item.copyWithNewState(!item.accessible)
        // Смена элемента и обновление списка
        val newList = currentList.toMutableList()
        newList[position] = updatedItem
        submitList(newList)
    }
}