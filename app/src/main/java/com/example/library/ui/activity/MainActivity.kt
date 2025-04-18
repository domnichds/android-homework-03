package com.example.library

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.library.data.DetailData
import com.example.library.ui.fragments.AddLibraryItemFragment
import com.example.library.ui.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    // Создание переменной для хранения ViewModel
    private lateinit var viewModel: LibraryViewModel

    // Создание переменной для хранения информации о текущей ориентации
    private var isLandscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установка нужного layout в зависимости от ориентации
        setAppropriateContentView()

        // Получение ViewModel для управления состоянием приложения
        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]

        // Определение альбомной ориентации приложения
        isLandscape = findViewById<View?>(R.id.detail_container) != null

        // Добавление фрагмента со списком элементов в нужный контейнер
        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.list_container, ListFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ListFragment())
                .commit()
        }

        // Подписка на изменение выбранного элемента для обновления UI
        viewModel.selectedDetailData.observe(this, Observer { detailData ->
            updateFragments(detailData, viewModel.isAddMode.value == true)
        })
        // Подписка на изменение режима добавления для обновления UI
        viewModel.isAddMode.observe(this, Observer { isAddMode ->
            updateFragments(viewModel.selectedDetailData.value, isAddMode)
        })
    }

    // Обновление фрагментов в зависимости от состояния ViewModel
    private fun updateFragments(detailData: DetailData?, isAddMode: Boolean) {
        if (isLandscape) {
            // Создание транзакции для управления правым контейнером
            val ft = supportFragmentManager.beginTransaction()
            val detailFrag = supportFragmentManager.findFragmentById(R.id.detail_container)
            // Очистка правой части экрана
            if (detailFrag != null) ft.remove(detailFrag)
            when {
                // Добавление фрагмента добавления при активном режиме добавления
                isAddMode -> ft.replace(R.id.detail_container, AddLibraryItemFragment.newInstance())
                // Добавление фрагмента деталей при выборе элемента
                detailData != null -> ft.replace(R.id.detail_container, DetailFragment.newInstance(
                    detailData.itemId, detailData.itemType, detailData.itemName,
                    detailData.author, detailData.pages, detailData.diskType,
                    detailData.issueNumber, detailData.month
                ))
                // Если ничего не выбрано и не активен режим добавления — фрагмент не добавляется
            }
            // Применение изменений фрагментов
            ft.commit()
        } else {
            // Получение текущего фрагмента в контейнере
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
            if (isAddMode) {
                // Добавление фрагмента добавления при его отсутствии
                if (currentFragment !is AddLibraryItemFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, AddLibraryItemFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
            } else if (detailData != null) {
                // Добавление фрагмента деталей при его отсутствии
                if (currentFragment !is DetailFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_container,
                            DetailFragment.newInstance(
                                itemId = detailData.itemId,
                                itemType = detailData.itemType,
                                itemName = detailData.itemName,
                                author = detailData.author,
                                pages = detailData.pages,
                                diskType = detailData.diskType,
                                issueNumber = detailData.issueNumber,
                                month = detailData.month
                            )
                        )
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                // Очистка back stack при отсутствии выбранного элемента и режима добавления
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    // Установка layout в зависимости от текущей ориентации экрана
    private fun setAppropriateContentView() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_land)
        } else {
            setContentView(R.layout.activity_main)
        }
    }

    // Обработка нажатия кнопки "назад"
    override fun onBackPressed() {

        when {
            viewModel.isAddMode.value == true -> viewModel.isAddMode.value = false
            viewModel.selectedDetailData.value != null -> viewModel.selectedDetailData.value = null
            else -> super.onBackPressed()
        }
    }
}