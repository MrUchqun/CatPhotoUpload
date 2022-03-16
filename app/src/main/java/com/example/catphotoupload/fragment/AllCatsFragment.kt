package com.example.catphotoupload.fragment

import android.annotation.SuppressLint
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
import com.example.catphotoupload.model.Breed
import com.example.catphotoupload.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllCatsFragment : Fragment() {

    companion object {
        fun newInstance(): AllCatsFragment {
            return AllCatsFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var allCatsAdapter: AllCatsAdapter
    private lateinit var lavLoading: LottieAnimationView

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allCatsAdapter = AllCatsAdapter(requireContext())
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
        recyclerView.adapter = allCatsAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    apiBreeds(page++)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        apiBreeds(page++)
    }

    private fun startAnim() {
        lavLoading.visibility = VISIBLE
        lavLoading.playAnimation()
    }

    private fun stopAnim() {
        lavLoading.pauseAnimation()
        lavLoading.visibility = GONE
    }

    private fun apiBreeds(page: Int) {
        startAnim()
        RetrofitHttp.photoService.getBreeds(15, page)
            .enqueue(object : Callback<ArrayList<Breed>> {
                override fun onResponse(
                    call: Call<ArrayList<Breed>>, response: Response<ArrayList<Breed>>
                ) {
                    stopAnim()
                    allCatsAdapter.addBreeds(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<Breed>>, t: Throwable) {
                    stopAnim()
                    Log.e("@@@", "onFailure: " + t.cause)
                    Log.e("@@@", "onFailure: " + t.message)
                }
            })
    }

}