package com.example.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LibraryAdapter(private val data: MutableList<LibraryItem>) :
    RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val idTextView: TextView = view.findViewById(R.id.idTextView)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val item = data[index]
        holder.nameTextView.text = item.name
        holder.idTextView.text = item.id.toString()
        holder.iconImageView.setImageResource(R.drawable.book)
    }

    override fun getItemCount(): Int = data.size
}
