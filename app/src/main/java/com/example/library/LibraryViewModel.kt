package com.example.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {
    // Репозиторий
    private val repository = ItemRepository()
    // LiveData для списка элементов
    private val _items = MutableLiveData<List<LibraryItem>>().apply { value = repository.getItems() }
    val items: LiveData<List<LibraryItem>> get() = _items

    // Метод для переключения состояния элемента
    fun toggleItemState(itemId: Int) {
        // Получение текущего элемента
        val currentList = _items.value ?: return
        val item = currentList.find { it.id == itemId } ?: return
        // Смена состояния найденного элемента
        val newState = !item.accessible
        // Обновление репозитория и подтягивание данных из него
        repository.updateItemState(itemId, newState)
        _items.value = repository.getItems()
    }

    // Метод для удаления элемента
    fun removeItem(itemId: Int) {
        repository.removeItem(itemId)
        _items.value = repository.getItems()
    }
}
