package com.example.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        // Костыль для прокрутки длинного текста
        mainFirstTextView.isSelected = true
        mainSecondTextView.isSelected = true


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

    companion object {
        fun createIntent(context: Context, item: LibraryItem): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra("ITEM_TYPE", when (item) {
                    is Book -> "Book"
                    is Disk -> "Disk"
                    is Newspaper -> "Newspaper"
                    else -> "Unknown"
                })
                putExtra("ITEM_ICON", when (item) {
                    is Book -> R.drawable.book
                    is Disk -> R.drawable.disk
                    is Newspaper -> R.drawable.newspaper
                    else -> R.drawable.unknown
                })
                putExtra("ITEM_NAME", item.name)
                putExtra("ITEM_ID", item.id)
                when (item) {
                    is Book -> {
                        putExtra("BOOK_AUTHOR", item.author)
                        putExtra("BOOK_NUMBER_OF_PAGES", item.numberOfPages)
                    }
                    is Disk -> {
                        putExtra("DISK_TYPE", if (item.type == 0) "CD" else "DVD")
                    }
                    is Newspaper -> {
                        putExtra("NEWSPAPER_MONTH", item.monthOfPublication.russianName)
                        putExtra("NEWSPAPER_ISSUE_NUMBER", item.issueNumber)
                    }
                    else -> {}
                }
            }
        }
    }
}