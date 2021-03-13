package com.example.newsshow.ui.news

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsshow.NewsApplication
import com.example.newsshow.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.arc_nac_header.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlin.concurrent.thread

class NewsFragment(private val type: String): Fragment() {



    private val viewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }
    private lateinit var adapter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchNews(type)
        swipeRefreshLayout.setOnRefreshListener {
            Thread.sleep(500)
            searchNews(type)
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun searchNews(type: String) {
        viewModel.searchNews(type)
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val news = result.getOrNull()
            if (news != null) {
                recyclerView.layoutManager = LinearLayoutManager(NewsApplication.context)
                adapter = NewsRecyclerAdapter(news)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }else {
                Toast.makeText(NewsApplication.context,"failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}