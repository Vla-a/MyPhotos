package com.example.myphotos.ui.image

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myphotos.adapters.PhotoAdapter
import com.example.myphotos.data.AddPhotoreqwest
import com.example.myphotos.data.Photo
import com.example.myphotos.data.User
import com.example.myphotos.databinding.ImageFragmentBinding
import com.example.myphotos.ui.UserActivity
import com.example.myphotos.utilites.Utility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.io.ByteArrayOutputStream
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import android.R
import com.example.myphotos.data.Note
import com.example.myphotos.ui.detailImage.NoteViewModel


class ImageFragment : Fragment() {

    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var list: MutableList<Photo> = mutableListOf()
    val imageViewModel: ImageViewModel by viewModel()
    val viewModel: NoteViewModel by viewModel()
    private var binding: ImageFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ImageFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity)

        val photoAdapter = PhotoAdapter(
            characterList = list,
            clickListener = ::clickListener,
            delete = ::clickListenerDeletePhoto
        )

        with(binding?.rvPhoto) {
            this?.layoutManager = context?.let { Utility.calculateNoOfColumns(it, 120f) }?.let {
                GridLayoutManager(
                    context,
                    it
                )
            }
            binding?.rvPhoto?.adapter = photoAdapter
        }

        imageViewModel.photoListLiveData.observe(this.viewLifecycleOwner, {
            photoAdapter.update(it)
        })

        binding!!.btnImage.setOnClickListener {
            openCamera()
        }
    }

    private fun clickListener(photo: Photo) {

        (context as UserActivity).supportFragmentManager
        this.findNavController().navigate(ImageFragmentDirections.toFragmentDetails())
        setFragmentResult(TEST, Bundle().apply {
            putString(KEY1, photo.url)
            putLong(KEY2, photo.id)
        })
    }

    private fun openCamera() {

        val bInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(bInt, REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (context?.applicationContext?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE
                    ), 2
                )
            }
        } else {
            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
                val image = data?.extras?.get("data") as Bitmap
//                val uri = this.context?.let { getImageUri(it, image) }

                val encodir = encodeImage(image)
                Log.e("KEK", "$encodir")

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        latitude = location!!.latitude
                        longitude = location!!.longitude

                        var date = Calendar.getInstance().time
                        val dateTimeAsLong = date.time/3600
                        val token = (activity as UserActivity).addToken()
                        val addPhoto =
                            AddPhotoreqwest(encodir.toString(),dateTimeAsLong, latitude, longitude)

                            imageViewModel.getAddPhoto(addPhoto, token)
                        imageViewModel.photoLiveData.observe(this.viewLifecycleOwner,{
                            imageViewModel.addPhotoToDatabase(it)
                        })
                    }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

//    // создать урл для базы данных
//    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path: String = MediaStore.Images.Media.insertImage(
//            inContext.contentResolver,
//            inImage,
//            "Title",
//            null
//        )
//        return Uri.parse(path)
//    }

    fun clickListenerDeletePhoto(photo: Photo) {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                imageViewModel.deletePhoto(photo)

                viewModel.noteListLiveData.observe(this.viewLifecycleOwner, {

                    val list = mutableListOf<Note>()
                    it.map { i ->
                            list.add(i)
                    }

                    list.forEach {
                       viewModel.deleteNote(it)
                        }
                })


                val token = (activity as UserActivity).addToken()
             imageViewModel.deletePhotoNetwork(photo.id,token)
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


    companion object {

        const val TEST = "TEST"
        const val KEY1 = "KEY1"
        const val KEY2 = "KEY2"
        private val REQUEST_TAKE_PHOTO = 24
        private val REQUEST_CODE = 24
    }
}










