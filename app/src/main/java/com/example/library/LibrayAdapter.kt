package com.example.library

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface  onItemClickListener {
    fun onItemClick(item: LibraryItem)
}

// Объявление класса адаптера. Поле - список предметов библиотеки
class LibraryAdapter(
    private val data: MutableList<LibraryItem>,
    private val itemClickListener: onItemClickListener
) : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {
    // Внутренний класс с сылками на элементы карточки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
        val item = data[index]
        holder.nameTextView.text = item.name
        holder.idTextView.text = item.id.toString()
        holder.iconImageView.setImageResource(R.drawable.book)

        // Привязка слушателя к методу onItemClick
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }
    // Переопределение метода для получения количества элементов в списке
    override fun getItemCount(): Int = data.size
}
