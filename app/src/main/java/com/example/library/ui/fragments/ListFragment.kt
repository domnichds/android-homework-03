package com.example.library.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

class ListFragment : Fragment() {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView

    // Переменная для хранения состояние списка
    private var listState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        recyclerView = view.findViewById(R.id.rv_fragment_list)
        val addButton: View = view.findViewById(R.id.b_item_edit_add_el)

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Создание и привязка колбека для свайпа элементов
        val swipeToDeleteCallback = SwipeToDeleteCallback(
            onSwipedCallback = { deletedItem ->
                viewModel.removeItem(deletedItem.id)
            },
            recyclerView = recyclerView
        )
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)

        // Создание адаптера и передача данных в фрагмент
        adapter = LibraryAdapter { selectedItem ->
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(
                itemId = selectedItem.id,
                itemType = when(selectedItem) {
                    is Book -> "Book"
                    is Disk -> "Disk"
                    is Newspaper -> "Newspaper"
                    else -> "unknown"
                },
                itemName = selectedItem.name,
                author = if (selectedItem is Book) selectedItem.author else null,
                pages = if (selectedItem is Book) selectedItem.numberOfPages else -1,
                diskType = if (selectedItem is Disk) { if(selectedItem.type == 0) "CD" else "DVD" } else null,
                issueNumber = if (selectedItem is Newspaper) selectedItem.issueNumber else -1,
                month = if (selectedItem is Newspaper) selectedItem.monthOfPublication.russianName else null
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        // Установка слушателя для изменения состояния списка
        viewModel.scrollToItemEvent.observe(viewLifecycleOwner) { newItemId ->
            newItemId?.let { id ->
                val currentItems = viewModel.items.value
                val position = currentItems?.indexOfFirst { it.id == id } ?: -1
                if (position >= 0) {
                    recyclerView.post {
                        recyclerView.smoothScrollToPosition(position)
                        // Сбрасываем событие после скролла
                        viewModel.clearScrollEvent()
                    }
                }
            }
        }

        // Установка слушателя за scroll событием
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items) {
                // Только если нет активного события скролла
                if (viewModel.scrollToItemEvent.value == null) {
                    listState?.let {
                        recyclerView.layoutManager?.onRestoreInstanceState(it)
                        listState = null
                    }
                }
            }
        }

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        // Сохранение состояния только если переходим НЕ на фрагмент добавления
        if (findNavController().currentDestination?.id != R.id.addFragment) {
            listState = recyclerView.layoutManager?.onSaveInstanceState()
        }
    }


}