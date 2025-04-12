package com.example.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {
    // Репозиторий
    private val repository = ItemRepository
    // LiveData для списка элементов
    private val _items = MutableLiveData<List<LibraryItem>>().apply { value = repository.getItems() }
    val items: LiveData<List<LibraryItem>> get() = _items

    // LiveData для события прокрутки к новому элементу
    private val _scrollToItemEvent = MutableLiveData<Int?>()
    val scrollToItemEvent: LiveData<Int?> get() = _scrollToItemEvent

    // Метод для удаления элемента
    fun removeItem(itemId: Int) {
        repository.removeItem(itemId)
        _items.value = repository.getItems()
    }

    // Метод для добавления элементов
    fun addItem(item: LibraryItem) {
        // Добавление и подтягивание данных из репозитория
        repository.addItem(item)
        refreshItems()

        _items.value?.lastOrNull()?.let {
            _scrollToItemEvent.value = it.id
        }
    }

    // Метод для подтягивания данных из репозитория
    fun refreshItems() {
        _items.value = repository.getItems()
    }

    fun clearScrollEvent() {
        _scrollToItemEvent.value = null
    }
}
