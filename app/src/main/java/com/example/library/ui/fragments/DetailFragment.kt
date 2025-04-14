package com.example.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    // Создание переменных для хранения ссылок на элементы интерфейса
    private lateinit var ivItemIcon: ImageView
    private lateinit var tvMainFirst: TextView
    private lateinit var tvMainSecond: TextView
    private lateinit var tvAddFirst: TextView
    private lateinit var tvAddSecond: TextView

    companion object {
        // Создание констант для ключей аргументов
        private const val ARG_ITEM_ID = "item_id"
        private const val ARG_ITEM_TYPE = "item_type"
        private const val ARG_ITEM_NAME = "item_name"
        private const val ARG_AUTHOR = "author"
        private const val ARG_PAGES = "pages"
        private const val ARG_DISK_TYPE = "disk_type"
        private const val ARG_ISSUE_NUMBER = "issue_number"
        private const val ARG_MONTH = "month"

        // Создание нового экземпляра фрагмента с передачей аргументов
        fun newInstance(
            itemId: Int,
            itemType: String,
            itemName: String,
            author: String? = null,
            pages: Int = -1,
            diskType: String? = null,
            issueNumber: Int = -1,
            month: String? = null
        ): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putInt(ARG_ITEM_ID, itemId)
            args.putString(ARG_ITEM_TYPE, itemType)
            args.putString(ARG_ITEM_NAME, itemName)
            args.putString(ARG_AUTHOR, author)
            args.putInt(ARG_PAGES, pages)
            args.putString(ARG_DISK_TYPE, diskType)
            args.putInt(ARG_ISSUE_NUMBER, issueNumber)
            args.putString(ARG_MONTH, month)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфляция layout для экрана деталей элемента
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Получение ссылок на элементы интерфейса
        ivItemIcon = view.findViewById(R.id.iv_item_icon)
        tvMainFirst = view.findViewById(R.id.tv_main_first)
        tvMainSecond = view.findViewById(R.id.tv_main_second)
        tvAddFirst = view.findViewById(R.id.tv_add_first)
        tvAddSecond = view.findViewById(R.id.tv_add_second)

        // Включение прокрутки текста для длинных названий
        tvMainFirst.isSelected = true
        tvMainSecond.isSelected = true

        // Получение аргументов, переданных во фрагмент
        val args = arguments

        // Извлечение данных об элементе из аргументов
        val itemType = args?.getString(ARG_ITEM_TYPE) ?: "unknown"
        val itemId = args?.getInt(ARG_ITEM_ID) ?: -1
        val itemName = args?.getString(ARG_ITEM_NAME) ?: ""
        val author = args?.getString(ARG_AUTHOR)
        val pages = args?.getInt(ARG_PAGES) ?: -1
        val diskType = args?.getString(ARG_DISK_TYPE)
        val issueNumber = args?.getInt(ARG_ISSUE_NUMBER) ?: -1
        val month = args?.getString(ARG_MONTH)

        // Отображение основной информации о названии
        tvMainFirst.text = itemName

        // Отображение информации в зависимости от типа элемента
        when (itemType) {
            "Book" -> {
                tvMainSecond.text = author ?: ""
                tvAddFirst.text = "Количество страниц: $pages"
                tvAddSecond.text = "ID: $itemId"
                ivItemIcon.setImageResource(R.drawable.book)
            }
            "Disk" -> {
                tvMainSecond.text = "Тип диска: ${diskType ?: "Unknown"}"
                tvAddFirst.text = "ID: $itemId"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.disk)
            }
            "Newspaper" -> {
                tvMainSecond.text = "Выпуск №$issueNumber, ${month ?: ""}"
                tvAddFirst.text = "ID: $itemId"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.newspaper)
            }
            else -> {
                tvMainSecond.text = ""
                tvAddFirst.text = "ID: $itemId"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.unknown)
            }
        }
    }
}