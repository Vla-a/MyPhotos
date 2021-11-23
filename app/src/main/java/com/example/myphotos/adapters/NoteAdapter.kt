package com.example.myphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotos.data.Note
import com.example.myphotos.databinding.ItemDetailBinding

class NoteAdapter (
    private val clickListener: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.MessageViewHolder>(DiffUtilItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder(
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context)), clickListener
        )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        private val bindingView: ItemDetailBinding,
        private val clickListener: (Note) -> Unit
    ) :
        RecyclerView.ViewHolder(bindingView.root) {

        fun bind(item: Note) {
            bindingView.text.text = item.note
            bindingView.tvDate.text = item.date

            itemView.setOnLongClickListener {
                clickListener(item)
                true
            }
        }
    }

class DiffUtilItemCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.date == newItem.date && oldItem.note == newItem.note
    }
}

}