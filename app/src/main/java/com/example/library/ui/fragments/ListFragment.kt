package com.example.library.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library.Book
import com.example.library.Disk
import com.example.library.LibraryAdapter
import com.example.library.LibraryViewModel
import com.example.library.Newspaper
import com.example.library.R
import com.example.library.SwipeToDeleteCallback
import com.example.library.data.DetailData

class ListFragment : Fragment() {

    // Создание переменной для хранения ViewModel
    private lateinit var viewModel: LibraryViewModel
    // Создание переменной для хранения адаптера списка
    private lateinit var adapter: LibraryAdapter
    // Создание переменной для хранения ссылки на RecyclerView
    private lateinit var recyclerView: RecyclerView

    // Создание переменной-флага для восстановления состояния скролла
    private var restoringScrollState = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфляция layout для экрана со списком элементов
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Получение ViewModel для доступа к состоянию библиотеки
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        // Получение ссылки на RecyclerView из layout
        recyclerView = view.findViewById(R.id.rv_fragment_list)
        // Получение ссылки на кнопку добавления
        val addButton: View = view.findViewById(R.id.b_item_edit_add_el)

        // Создание и установка LayoutManager для вертикального списка
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Восстановление состояния скролла из ViewModel, если оно есть
        val initialScrollState = viewModel.listScrollState.value
        if (initialScrollState != null) {
            restoringScrollState = true
            recyclerView.layoutManager?.onRestoreInstanceState(initialScrollState)
        }

        // Создание и привязка callback для swipe-to-delete
        val swipeToDeleteCallback = SwipeToDeleteCallback(
            onSwipedCallback = { deletedItem ->
                viewModel.removeItem(deletedItem.id)
            },
            recyclerView = recyclerView
        )
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)

        // Создание адаптера и установка обработчика выбора элемента
        adapter = LibraryAdapter { selectedItem ->
            viewModel.isAddMode.value = false
            viewModel.selectedDetailData.value = DetailData(
                itemId = selectedItem.id,
                itemType = when (selectedItem) {
                    is Book -> "Book"
                    is Disk -> "Disk"
                    is Newspaper -> "Newspaper"
                    else -> "unknown"
                },
                itemName = selectedItem.name,
                author = if (selectedItem is Book) selectedItem.author else null,
                pages = if (selectedItem is Book) selectedItem.numberOfPages else -1,
                diskType = if (selectedItem is Disk) if (selectedItem.type == 0) "CD" else "DVD" else null,
                issueNumber = if (selectedItem is Newspaper) selectedItem.issueNumber else -1,
                month = if (selectedItem is Newspaper) selectedItem.monthOfPublication.russianName else null
            )
        }
        // Установка адаптера для RecyclerView
        recyclerView.adapter = adapter

        // Подписка на событие автоскролла к новому элементу
        viewModel.scrollToItemEvent.observe(viewLifecycleOwner) { newItemId ->
            newItemId?.let { id ->
                val currentItems = viewModel.items.value
                val position = currentItems?.indexOfFirst { it.id == id } ?: -1
                if (position >= 0) {
                    recyclerView.post {
                        recyclerView.smoothScrollToPosition(position)
                        viewModel.clearScrollEvent()
                    }
                }
            }
        }

        // Подписка на обновление списка элементов
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items) {
                // Восстановление состояния скролла после загрузки списка
                if (viewModel.scrollToItemEvent.value == null && restoringScrollState) {
                    viewModel.listScrollState.value?.let {
                        recyclerView.layoutManager?.onRestoreInstanceState(it)
                    }
                    restoringScrollState = false
                }
            }
        }

        // Установка обработчика для кнопки добавления элемента
        addButton.setOnClickListener {
            viewModel.selectedDetailData.value = null
            viewModel.isAddMode.value = true
        }
    }

    override fun onStop() {
        super.onStop()
        // Сохранение состояния скролла при уходе с экрана
        val state = recyclerView.layoutManager?.onSaveInstanceState()
        viewModel.setListScrollState(state)
    }
}