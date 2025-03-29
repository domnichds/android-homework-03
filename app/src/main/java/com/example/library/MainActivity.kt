package com.example.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

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

        val adapter = LibraryAdapter(libraryItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}