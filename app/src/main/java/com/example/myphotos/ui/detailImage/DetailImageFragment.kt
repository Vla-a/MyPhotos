package com.example.myphotos.ui.detailImage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myphotos.adapters.NoteAdapter
import com.example.myphotos.data.Note
import com.example.myphotos.databinding.FragmentDetailImageBinding
import com.example.myphotos.ui.UserActivity
import com.example.myphotos.ui.image.ImageFragment.Companion.KEY1
import com.example.myphotos.ui.image.ImageFragment.Companion.KEY2
import com.example.myphotos.ui.image.ImageFragment.Companion.TEST
import org.koin.android.viewmodel.ext.android.viewModel

class DetailImageFragment : Fragment() {

    private var binding: FragmentDetailImageBinding? = null
    val viewModel: NoteViewModel by viewModel()
    val noteAdapter = NoteAdapter { clickListener(it) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailImageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(TEST) { key, bundle ->
            val uri: String = bundle.getString(KEY1) as String
            val date: String = bundle.getString(KEY2) as String

            binding!!.rvDetailPhoto.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding!!.rvDetailPhoto.adapter = noteAdapter

            viewModel.noteListLiveData.observe(this.viewLifecycleOwner, {

                val list = mutableListOf<Note>()
                it.map { i ->
                    if (i.userPhoto == uri) {
                        list.add(i)
                        noteAdapter.submitList(list)
                    }

                }

            })

            binding!!.btnNote.setOnClickListener {
                val newNote = Note(binding!!.edit.text.toString(), date, uri)
                if (binding!!.edit.text.toString().trim().isNotBlank()) {
                    addNote(newNote)
                    binding!!.edit.setText("")
                } else {
                    Toast.makeText(context, "No text", Toast.LENGTH_SHORT).show()
                }
            }

            this.context?.let {
                Glide
                    .with(it)
                    .load(uri)
                    .into(binding!!.imageView)
            }

            binding!!.textDate.text = date
        }
    }

    fun clickListener(note: Note) {

        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                viewModel.deleteNote(note)
                onStart()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun addNote(note: Note) {
        viewModel.addPhotoToDatabase(note)
        (context as UserActivity).supportFragmentManager

    }

}
