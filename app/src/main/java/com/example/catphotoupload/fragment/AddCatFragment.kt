package com.example.catphotoupload.fragment

import android.R.attr.path
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.catphotoupload.R
import com.example.catphotoupload.networking.RetrofitHttp
import com.kusu.loadingbutton.LoadingButton
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.FishBun.Companion.INTENT_PATH
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    private lateinit var btnUpload: LoadingButton

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


        btnUpload.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    private val pickImage = 100

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            val file = getFile(data?.data!!)
            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/jpg"), file)
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, reqFile)

            val responseBodyCall: Call<ResponseBody> = RetrofitHttp.photoService.uploadFile(body, file.name)

            responseBodyCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("Success", "success " + response.code());
                    Log.d("Success", "success " + response.message());
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("failure", "message = " + t.message);
                    Log.e("failure", "cause = " + t.cause);
                }

            })
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

}