package com.example.library

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), onItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установка макета activity_main
        setContentView(R.layout.activity_main)
        // Поиск элемента recyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // Тестовые данные
        val libraryItems: MutableList<LibraryItem> = mutableListOf(
            Book(0, "Преступление и наказание", true, 300, "Достоевский"),
            Book(0, "Война и мир", true, 12000, "Толстой"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл"),
            Book(0, "1984", true, 400, "Оруэл")
        )
        // Создание адаптера и передача ему списка объектов
        val adapter = LibraryAdapter(libraryItems, this)
        // Указание recyclerView правила располагания элементов (список в данном случае)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Установка адаптера для recyclerView
        recyclerView.adapter = adapter
    }

    override fun onItemClick(item: LibraryItem) {
        // Вывод короткого тоста с текстом
        Toast.makeText(this, "Элемент с id ${item.id}", Toast.LENGTH_SHORT).show()
    }
}