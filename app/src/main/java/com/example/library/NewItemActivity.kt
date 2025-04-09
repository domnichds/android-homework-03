package com.example.library

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class NewItemActivity : AppCompatActivity() {

    private lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_edit)

        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]

        val elementTypeSpinner: Spinner = findViewById(R.id.s_item_edit_element_type)
        val field1EditText: EditText = findViewById(R.id.ett_item_edit_field_2)
        val field2EditText: EditText = findViewById(R.id.ett_item_edit_field_3)
        val field3EditText: EditText = findViewById(R.id.ett_item_edit_field_4)
        val saveButton: Button = findViewById(R.id.b_item_edit_add_el)

        // Настройка Spinner с типами элементов
        val spinnerItems = listOf(
            SpinnerItem(R.drawable.book, "Книга"),
            SpinnerItem(R.drawable.disk, "Диск"),
            SpinnerItem(R.drawable.newspaper, "Газета")
        )
        val adapter = SpinnerItemAdapter(this, spinnerItems)
        elementTypeSpinner.adapter = adapter

        // Слушатель для изменения типа элемента
        elementTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as SpinnerItem
                when (selectedItem.text) {
                    "Книга" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Автор"
                        field3EditText.hint = "Количество страниц"
                        field2EditText.visibility = View.VISIBLE
                    }
                    "Диск" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Тип (CD или DVD)"
                        field3EditText.visibility = View.GONE
                    }
                    "Газета" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Номер выпуска"
                        field3EditText.hint = "Месяц выпуска"
                        field3EditText.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Обработчик для кнопки сохранения
        saveButton.setOnClickListener {
            // Логика для создания нового элемента и сохранения его в библиотеке
            val itemType = (elementTypeSpinner.selectedItem as SpinnerItem).text
            val field1 = field1EditText.text.toString()
            val field2 = field2EditText.text.toString()
            val field3 = field3EditText.text.toString()

            val context = applicationContext

            // Создание нового элемента в зависимости от типа
            val newItem: LibraryItem? = when (itemType) {
                "Книга" -> {
                    val numberOfPages = field3.toIntOrNull()
                    if (numberOfPages != null) {
                        Book(0, field1, true, numberOfPages, field2)
                    } else {
                        Toast.makeText(context, "Количество страниц должно быть числом", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                "Диск" -> {
                    if (field2 == "CD" || field2 == "DVD") {
                        Disk(0, field1, true, if (field2 == "CD") 0 else 1)
                    } else {
                        Toast.makeText(context, "Тип диска должен быть CD или DVD", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                "Газета" -> {
                    val issueNumber = field2.toIntOrNull()
                    val month = Month.entries.find { it.russianName == field3 }
                    if (issueNumber != null && month != null) {
                        Newspaper(0, field1, true, issueNumber, month)
                    } else {
                        Toast.makeText(context, "Введите правильный номер выпуска или месяц", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                else -> null
            }

            // Сохранение элемента в библиотеке (например, в базе данных или списке)
            if (newItem != null) {
                viewModel.addItem(newItem)
                finish() // Завершение активити
            }
        }
    }
}