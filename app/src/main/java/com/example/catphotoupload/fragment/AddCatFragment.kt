package com.example.catphotoupload.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.catphotoupload.R
import com.example.catphotoupload.networking.RetrofitHttp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


class AddCatFragment : Fragment() {

    companion object {
        fun newInstance(): AddCatFragment {
            return AddCatFragment()
        }
    }

    private lateinit var ivPhoto: ImageView
    private lateinit var llPreview: LinearLayout
    private lateinit var btnUpload: Button
    private lateinit var progressBar: ProgressBar
    private val pickImage = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        ivPhoto = view.findViewById(R.id.iv_photo)
        llPreview = view.findViewById(R.id.ll_preview)
        btnUpload = view.findViewById(R.id.btn_upload)
        progressBar = view.findViewById(R.id.progressBar)

        btnUpload.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            llPreview.visibility = GONE
            ivPhoto.setImageURI(data?.data)
            btnUpload.setText(R.string.txt_upload)

            btnUpload.setOnClickListener {
                apiUploadImage(getFile(data?.data!!))
            }
        }
    }

    private fun getFile(uri: Uri): File {
        val ins = requireActivity().contentResolver.openInputStream(uri)
        val file = File.createTempFile(
            "file",
            ".jpg",
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(file)
        ins?.copyTo(fileOutputStream)
        ins?.close()
        fileOutputStream.close()
        return file
    }

    private fun apiUploadImage(file: File) {
        showLoading()
        val responseBodyCall: Call<ResponseBody> =
            RetrofitHttp.photoService.uploadFile(
                RetrofitHttp.getMultipartBody("file", file),
                file.name
            )

        responseBodyCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                hideLoading(R.string.txt_uploaded)
                btnUpload.setTextColor(Color.GREEN)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                hideLoading(R.string.txt_uploading_fail)
                btnUpload.setTextColor(Color.RED)
            }

        })
    }

    private fun showLoading() {
        btnUpload.text = ""
        progressBar.visibility = VISIBLE
    }

    private fun hideLoading(text: Int) {
        btnUpload.setText(text)
        progressBar.visibility = INVISIBLE
    }
}