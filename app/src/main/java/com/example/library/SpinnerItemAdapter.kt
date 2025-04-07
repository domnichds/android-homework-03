package com.example.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter

data class SpinnerItem(
    val iconResId: Int,
    val text: String
)

class SpinnerItemAdapter(context: Context, private val items: List<SpinnerItem>) :
    ArrayAdapter<SpinnerItem>(context, 0, items), SpinnerAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val item = getItem(position)
        if (item != null) {
            val iconImageView: ImageView = view.findViewById(R.id.iv_spinner_item_icon)
            val textView: TextView = view.findViewById(R.id.tv_spinner_item_text)

            iconImageView.setImageResource(item.iconResId)
            textView.text = item.text
        }
        return view
    }
}
