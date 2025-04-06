package com.example.library

import android.os.Bundle
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
        val adapter = LibraryAdapter {id -> onItemClick(id)}
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
    }

    private fun onItemClick(id: Int) {
        viewModel.toggleItemState(id)
        Toast.makeText(this, "Элемент с id $id", Toast.LENGTH_SHORT).show()
    }
}