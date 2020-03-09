package com.example.gitrepos.view.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gitrepos.R
import com.example.gitrepos.model.profile.ProfilesDatabase
import kotlinx.android.synthetic.main.fragment_dialog.*
import java.io.ByteArrayOutputStream


class MyDialogFragment : DialogFragment() {

    private val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1
    private val GALLERY_ACTIVITY_REQUEST_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera.setOnClickListener {
            Log.d("Dialog", "Camera")
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        }

        galery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(galleryIntent, GALLERY_ACTIVITY_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {

                    val stream = ByteArrayOutputStream()
                    val bmp = data.extras!!["data"] as Bitmap?
                    bmp!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray: ByteArray = stream.toByteArray()

                    saveAvatar(byteArray)
                }
            }

            if (requestCode == GALLERY_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {


                    val stream = ByteArrayOutputStream()
                    val returnUri: Uri? = data.data
                    val bmp = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver,
                        returnUri
                    )
                    bmp!!.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                    val byteArray: ByteArray = stream.toByteArray()

                    saveAvatar(byteArray)

                }
            }
        }
        catch (e: Exception) {

        }

    }

    private fun saveAvatar(byteArray: ByteArray){
        val profile = ProfilesDatabase.getDatabase(requireContext()).profilesDao().getProfile()
        profile.avatar = byteArray
        ProfilesDatabase.getDatabase(requireContext()).profilesDao().update(profile)
    }
}