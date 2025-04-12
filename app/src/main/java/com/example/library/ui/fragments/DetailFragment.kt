package com.example.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    private lateinit var ivItemIcon: ImageView
    private lateinit var tvMainFirst: TextView
    private lateinit var tvMainSecond: TextView
    private lateinit var tvAddFirst: TextView
    private lateinit var tvAddSecond: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ivItemIcon = view.findViewById(R.id.iv_item_icon)
        tvMainFirst = view.findViewById(R.id.tv_main_first)
        tvMainSecond = view.findViewById(R.id.tv_main_second)
        tvAddFirst = view.findViewById(R.id.tv_add_first)
        tvAddSecond = view.findViewById(R.id.tv_add_second)

        // Костыль для прокрутки длинного текста
        tvMainFirst.isSelected = true
        tvMainSecond.isSelected = true

        // Получение аргументов, переданных через safeArgs
        val args = DetailFragmentArgs.fromBundle(requireArguments())

        tvMainFirst.text = args.itemName

        // Обработка данных в зависимости от типа
        when (args.itemType) {
            "Book" -> {
                tvMainSecond.text = args.author ?: ""
                tvAddFirst.text = "Количество страниц: ${args.pages}"
                tvAddSecond.text = "ID: ${args.itemId}"
                ivItemIcon.setImageResource(R.drawable.book)
            }
            "Disk" -> {
                tvMainSecond.text = "Тип диска: ${args.diskType ?: "Unknown"}"
                tvAddFirst.text = "ID: ${args.itemId}"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.disk)
            }
            "Newspaper" -> {
                tvMainSecond.text = "Выпуск №${args.issueNumber}, ${args.month ?: ""}"
                tvAddFirst.text = "ID: ${args.itemId}"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.newspaper)
            }
            else -> {
                tvMainSecond.text = ""
                tvAddFirst.text = "ID: ${args.itemId}"
                tvAddSecond.text = ""
                ivItemIcon.setImageResource(R.drawable.unknown)
            }
        }
    }
}
