package com.example.notesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ListItemBinding
import com.example.notesapp.model.Notes

class AppRecyclerViewAdapter(
    val list: MutableList<Notes>,
    private val deleteListener: DeleteClickListener
) :
    RecyclerView.Adapter<AppRecyclerViewAdapter.AppViewHolder>() {

    class AppViewHolder(val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    interface DeleteClickListener {
        fun onItemClick(note: Notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val note = list[position]
        holder.itemBinding.apply {
            tvNumber.text = (position + 1).toString()
            tvDate.text = note.date
            tvHeading2.text = note.heading
            tvBody2.text = note.body
            spinnerType.text = note.type

            root.setOnClickListener {
                onItemClickListener?.let { it(note) }
            }
            ivDelete.setOnClickListener {
                deleteListener.onItemClick(note)
            }
        }
    }

    private var onItemClickListener: ((Notes) -> Unit)? = null

    fun setOnItemClickListener(listener: (Notes) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }
}