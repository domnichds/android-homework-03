package com.example.library

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.viewmodel.LibraryViewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var adapter: LibraryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установка макета activity_main
        setContentView(R.layout.activity_main)
        // Поиск элемента recyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // Указание recyclerView правила располагания элементов (список в данном случае)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Создание и установка адаптера
        val adapter = LibraryAdapter(this)
        recyclerView.adapter = adapter
        // Получение ViewModel
        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        // Наблюдение за списком элементов
        viewModel.items.observe(this, Observer { items ->
            adapter.submitList(items)
        })

        // Реализация swipeCallback
        val swipeCallback = SwipeToDeleteCallback( { item ->
            viewModel.removeItem(item.id)
            Toast.makeText(this, "Элемент с id ${item.id} удален", Toast.LENGTH_SHORT).show()
        }, recyclerView)

        // Привязка swipeCallback
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(id: Int) {
        // Смена состояния объекта
        viewModel.toggleItemState(id)
        // Вывод короткого тоста с текстом
        Toast.makeText(this, "Элемент с id $id", Toast.LENGTH_SHORT).show()
    }
}