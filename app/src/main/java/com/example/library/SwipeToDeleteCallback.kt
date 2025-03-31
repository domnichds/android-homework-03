package com.example.library

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: LibraryAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
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
        // Получение позиции элемента и его удаление
        val position = viewHolder.adapterPosition
        adapter.removeItem(position)
    }
}