package com.example.myphotos.ui.image

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myphotos.R
import com.example.myphotos.data.Photo
import com.example.myphotos.adapters.PhotoAdapter
import com.example.myphotos.utilites.Utility
import com.example.myphotos.databinding.ImageFragmentBinding
import com.example.myphotos.ui.UserActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.JsonObject
import kotlinx.io.ByteArrayOutputStream
import org.json.JSONException
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class ImageFragment : Fragment() {

    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var list: MutableList<Photo> = mutableListOf()
    val imageViewModel: ImageViewModel by viewModel()
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
            putString(KEY2, photo.date)
        })
    }

    private fun openCamera() {

        val bInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(bInt, REQUEST_CODE)
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            val image = data?.extras?.get("data")
            val uri = this.context?.let { getImageUri(it, image as Bitmap) }

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    latitude = location!!.latitude
                    longitude = location!!.longitude
                    imageViewModel.addPhotoToDatabase(uri.toString(), latitude, longitude)
                }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun clickListenerDeletePhoto(photo: Photo) {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                imageViewModel.deletePhoto(photo)
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun getAddPhoto(image: Bitmap): JsonObject {
        val time = Calendar.getInstance().timeInMillis
        val p = R.drawable.photo
        val `object` = JsonObject()
        try {

            `object`.addProperty("base64Image", "$p")
            `object`.addProperty("date", "1262307723")
            `object`.addProperty("lat", "53.9")
            `object`.addProperty("lng", "27.5667")
        } catch (e: JSONException) {

            e.printStackTrace()
        }
        Log.v("JObj", "$`object`")
        return `object`
    }

    companion object {

        const val TEST = "TEST"
        const val KEY1 = "KEY1"
        const val KEY2 = "KEY2"
        private val REQUEST_TAKE_PHOTO = 24
        private val REQUEST_CODE = 24
    }
}













