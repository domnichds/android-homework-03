package com.example.library

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.data.DetailData

class LibraryViewModel : ViewModel() {
    // Создание ссылки на репозиторий с элементами библиотеки
    private val repository = ItemRepository

    // Создание LiveData для хранения списка элементов библиотеки
    private val _items = MutableLiveData<List<LibraryItem>>().apply { value = repository.getItems() }
    val items: LiveData<List<LibraryItem>> get() = _items

    // Создание LiveData для хранения события прокрутки к новому элементу
    private val _scrollToItemEvent = MutableLiveData<Int?>()
    val scrollToItemEvent: LiveData<Int?> get() = _scrollToItemEvent

    // Создание LiveData для хранения состояния скролла списка
    private val _listScrollState = MutableLiveData<Parcelable?>()
    val listScrollState: LiveData<Parcelable?> get() = _listScrollState

    // Установка состояния скролла списка
    fun setListScrollState(state: Parcelable?) {
        _listScrollState.value = state
    }

    // Создание LiveData для хранения состояния выбранного элемента (деталей)
    val selectedDetailData = MutableLiveData<DetailData?>(null)
    // Создание LiveData для хранения состояния режима добавления
    val isAddMode = MutableLiveData<Boolean>(false)

    // Удаление элемента из репозитория и обновление списка
    fun removeItem(itemId: Int) {
        repository.removeItem(itemId)
        _items.value = repository.getItems()
    }

    // Добавление элемента в репозиторий, обновление списка и запуск события автоскролла к новому элементу
    fun addItem(item: LibraryItem) {
        repository.addItem(item)
        refreshItems()
        _items.value?.lastOrNull()?.let {
            _scrollToItemEvent.value = it.id
        }
    }

    // Обновление списка элементов из репозитория
    fun refreshItems() {
        _items.value = repository.getItems()
    }

    // Сброс события автоскролла
    fun clearScrollEvent() {
        _scrollToItemEvent.value = null
    }
}