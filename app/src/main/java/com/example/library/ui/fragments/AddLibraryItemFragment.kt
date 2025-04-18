package com.example.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.library.Book
import com.example.library.Disk
import com.example.library.LibraryItem
import com.example.library.LibraryViewModel
import com.example.library.Month
import com.example.library.Newspaper
import com.example.library.R
import com.example.library.SpinnerItem
import com.example.library.SpinnerItemAdapter
import com.example.library.data.DetailData

class AddLibraryItemFragment : Fragment() {

    // Создание переменной для хранения ViewModel
    private lateinit var viewModel: LibraryViewModel

    companion object {
        // Создание нового экземпляра фрагмента для добавления элемента
        fun newInstance(): AddLibraryItemFragment {
            return AddLibraryItemFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        // Инфляция layout для экрана добавления элемента
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Получение ViewModel для управления состоянием библиотеки
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]

        // Получение ссылок на элементы интерфейса
        val elTypeSpinner: Spinner = view.findViewById(R.id.s_item_edit_element_type)
        val field1EditText: EditText = view.findViewById(R.id.ett_item_edit_field_2)
        val field2EditText: EditText = view.findViewById(R.id.ett_item_edit_field_3)
        val field3EditText: EditText = view.findViewById(R.id.ett_item_edit_field_4)
        val saveButton: Button = view.findViewById(R.id.b_item_edit_add_el)

        // Создание списка вариантов для выбора типа элемента
        val spinnerItems = listOf(
            SpinnerItem(R.drawable.book, "Книга"),
            SpinnerItem(R.drawable.disk, "Диск"),
            SpinnerItem(R.drawable.newspaper, "Газета")
        )
        // Установка адаптера для spinner'а с иконками и подписями
        val spinnerAdapter = SpinnerItemAdapter(requireContext(), spinnerItems)
        elTypeSpinner.adapter = spinnerAdapter

        // Обработка выбора типа элемента в spinner'е
        elTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position) as SpinnerItem
                // Установка подсказок и видимости полей в зависимости от выбранного типа
                when (selectedItem.text) {
                    "Книга" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Автор"
                        field3EditText.hint = "Количество страниц"
                        field2EditText.visibility = View.VISIBLE
                        field3EditText.visibility = View.VISIBLE
                    }
                    "Диск" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Тип (CD или DVD)"
                        field2EditText.visibility = View.VISIBLE
                        field3EditText.visibility = View.GONE
                    }
                    "Газета" -> {
                        field1EditText.hint = "Название"
                        field2EditText.hint = "Номер выпуска"
                        field3EditText.hint = "Месяц выпуска"
                        field2EditText.visibility = View.VISIBLE
                        field3EditText.visibility = View.VISIBLE
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Обработка нажатия кнопки "Сохранить"
        saveButton.setOnClickListener {
            // Получение выбранного типа элемента
            val selectedType = (elTypeSpinner.selectedItem as SpinnerItem).text
            // Получение введённых пользователем данных
            val field1 = field1EditText.text.toString().trim()
            val field2 = field2EditText.text.toString().trim()
            val field3 = field3EditText.text.toString().trim()

            // Создание нового элемента библиотеки в зависимости от выбранного типа
            val newItem: LibraryItem? = when (selectedType) {
                "Книга" -> {
                    val numberOfPages = field3.toIntOrNull()
                    if (field1.isNotEmpty() && field2.isNotEmpty() && numberOfPages != null) {
                        Book(0, field1, true, numberOfPages, field2)
                    } else {
                        Toast.makeText(requireContext(), "Неверные поля для книги", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                "Диск" -> {
                    if (field1.isNotEmpty() && (field2 == "CD" || field2 == "DVD")) {
                        Disk(0, field1, true, if (field2 == "CD") 0 else 1)
                    } else {
                        Toast.makeText(requireContext(), "Неверные поля для диска", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                "Газета" -> {
                    val issueNumber = field2.toIntOrNull()
                    val month = Month.entries.find { it.russianName.equals(field3, ignoreCase = true) }
                    if (field1.isNotEmpty() && issueNumber != null && month != null) {
                        Newspaper(0, field1, true, issueNumber, month)
                    } else {
                        Toast.makeText(requireContext(), "Неверный месяц или номер выпуска", Toast.LENGTH_SHORT).show()
                        null
                    }
                }
                else -> null
            }

            // Добавление элемента в библиотеку, если данные корректны
            if (newItem != null) {
                viewModel.addItem(newItem)
                // Получение id нового элемента
                val newId = viewModel.items.value?.lastOrNull()?.id ?: 0

                // Выход из режима добавления
                viewModel.isAddMode.value = false

                // Выделение нового элемента для отображения деталей
                val item = viewModel.items.value?.find { it.id == newId }
                if (item != null) {
                    viewModel.selectedDetailData.value = DetailData(
                        itemId = item.id,
                        itemType = when (item) {
                            is Book -> "Book"
                            is Disk -> "Disk"
                            is Newspaper -> "Newspaper"
                            else -> "unknown"
                        },
                        itemName = item.name,
                        author = if (item is Book) item.author else null,
                        pages = if (item is Book) item.numberOfPages else -1,
                        diskType = if (item is Disk) if (item.type == 0) "CD" else "DVD" else null,
                        issueNumber = if (item is Newspaper) item.issueNumber else -1,
                        month = if (item is Newspaper) item.monthOfPublication.russianName else null
                    )
                } else {
                    // Если элемент не найден, сбрасывается выделение
                    viewModel.selectedDetailData.value = null
                }
            }
        }
    }
}