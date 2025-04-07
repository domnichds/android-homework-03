package com.example.library

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.rv_library_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = LibraryAdapter()
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]

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

        // Обработчик нажатий на кнопку добавления элемента
        val addButton: Button = findViewById(R.id.b_item_edit_add_el)
        addButton.setOnClickListener {
            val intent = Intent(this, NewItemActivity::class.java)
            startActivity(intent)
        }
    }

    // Вызывается при переходе из окна добавления и обновляет список
    override fun onResume() {
        super.onResume()
        viewModel.refreshItems()
    }

}