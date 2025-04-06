package com.example.library

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val onSwipedCallback: (LibraryItem) -> Unit,
    private val recyclerView: RecyclerView
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    // Переопределение метода для перемещения элементов (запрещает перемещение возвратом false)
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // Переопределение метода для свайпа
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Получение адаптера элемента
        val position = viewHolder.adapterPosition
        val context = viewHolder.itemView.context
        val adapter = recyclerView.adapter as? LibraryAdapter

        // Передача элемента в onSwipedCallback
        if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
            onSwipedCallback(adapter?.currentList?.get(viewHolder.adapterPosition)!!)
        }
    }
}
