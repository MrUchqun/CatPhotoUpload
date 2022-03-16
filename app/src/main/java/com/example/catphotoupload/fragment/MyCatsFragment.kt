package com.example.catphotoupload.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.catphotoupload.R
import com.example.catphotoupload.adapter.AllCatsAdapter
import com.example.catphotoupload.adapter.MyCatsAdapter
import com.example.catphotoupload.model.Breed
import com.example.catphotoupload.model.Image
import com.example.catphotoupload.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyCatsFragment : Fragment() {

    companion object {
        fun newInstance(): MyCatsFragment {
            return MyCatsFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var myCatsAdapter: MyCatsAdapter
    private lateinit var lavLoading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCatsAdapter = MyCatsAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        lavLoading = view.findViewById(R.id.lav_loading)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = myCatsAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    apiImages()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        apiImages()
    }

    private fun startAnim() {
        lavLoading.visibility = VISIBLE
        lavLoading.playAnimation()
    }

    private fun stopAnim() {
        lavLoading.pauseAnimation()
        lavLoading.visibility = GONE
    }

    private fun apiImages() {
        startAnim()
        RetrofitHttp.photoService.getMyCats()
            .enqueue(object : Callback<ArrayList<Image>> {
                override fun onResponse(
                    call: Call<ArrayList<Image>>, response: Response<ArrayList<Image>>
                ) {
                    stopAnim()
                    myCatsAdapter.addImages(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<Image>>, t: Throwable) {
                    stopAnim()
                    Log.e("@@@", "onFailure: " + t.cause)
                    Log.e("@@@", "onFailure: " + t.message)
                }
            })
    }

}