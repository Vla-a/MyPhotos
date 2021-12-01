package com.example.myphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myphotos.data.Photo
import com.example.myphotos.databinding.ItemPhotoBinding
import java.text.SimpleDateFormat
import java.util.*

class PhotoAdapter(
    private val characterList: MutableList<Photo> = mutableListOf(),
    private val clickListener: (Photo) -> Unit,
    private val delete: (Photo) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)), clickListener, delete
        )


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    fun update(newCharacterList: List<Photo>) {
        characterList.clear()
        characterList.addAll(newCharacterList)
        notifyDataSetChanged()

    }

    class PhotoViewHolder(
        private val bindingView: ItemPhotoBinding,
        private val clickListener: (Photo) -> Unit,
        private val delete: (Photo) -> Unit
    ) : RecyclerView.ViewHolder(bindingView.root) {

        fun bind(item: Photo) {
            bindingView.textView2.text = convertLongToTime(item.date*3600)

            Glide
                .with(itemView.context)
                .load(item.url)
                .into(bindingView.imageView)


            itemView.setOnClickListener {
                clickListener(item)
            }
            itemView.setOnLongClickListener {
                delete(item)
                true
            }
        }
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }
    }
}

