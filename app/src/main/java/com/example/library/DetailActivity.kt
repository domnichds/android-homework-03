package com.example.library

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_info)

        // Получение данных из Intent
        val itemType = intent.getStringExtra("ITEM_TYPE")
        val itemIcon = intent.getIntExtra("ITEM_ICON", R.drawable.unknown)
        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemId = intent.getIntExtra("ITEM_ID", -1)

        // Поиск элементов представления
        val itemIconView: ImageView = findViewById(R.id.iv_item_icon)
        val mainFirstTextView: TextView = findViewById(R.id.tv_main_first)
        val mainSecondTextView: TextView = findViewById(R.id.tv_main_second)
        val addFirstTextView: TextView = findViewById(R.id.tv_add_first)
        val addSecondTextView: TextView = findViewById(R.id.tv_add_second)

        // Установка данных в элементы представления
        itemIconView.setImageResource(itemIcon)
        mainFirstTextView.text = itemName

        // Ветвление данных в зависимости от типа элемента
        when (itemType) {
            "Book" -> {
                val author = intent.getStringExtra("BOOK_AUTHOR")
                val numberOfPages = intent.getIntExtra("BOOK_NUMBER_OF_PAGES", 0)
                mainSecondTextView.text = author
                addFirstTextView.text = "Количество страниц: $numberOfPages"
                addSecondTextView.text = "ID: $itemId"
            }
            "Disk" -> {
                val diskType = intent.getStringExtra("DISK_TYPE")
                mainSecondTextView.text = "Тип диска: $diskType"
                addFirstTextView.text = "ID: $itemId"
            }
            "Newspaper" -> {
                val monthOfPublication = intent.getStringExtra("NEWSPAPER_MONTH")
                val issueNumber = intent.getIntExtra("NEWSPAPER_ISSUE_NUMBER", -1)
                mainSecondTextView.text = "Выпуск №$issueNumber, $monthOfPublication"
                addFirstTextView.text = "ID: $itemId"
            }
            else -> {}
        }
    }
}