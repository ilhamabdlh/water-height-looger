package com.ilhamabdlh.waterheight.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ilhamabdlh.waterheight.FetchHeightJobService
import com.ilhamabdlh.waterheight.R
import com.ilhamabdlh.waterheight.data.Post
import com.ilhamabdlh.waterheight.setupVerticalAdapter
import com.ilhamabdlh.waterheight.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private lateinit var viewModel: MainViewModel
    private val posts = mutableListOf(
        Post(1, "Post Pemantauan Fakultas Teknik" , "JL Rawamangun Selatan", 0, true),
        Post(2, "Post Pemantauan Rektorat" , "JL Rawamangun Timur", 0, false),
        Post(3, "Post Pemantauan Rawamangun Muka" , "JL Rawamangun Muka", 0, false)
    )
    private val adapter by lazy { MainAdapter(posts, onClickListener) }

    private val onClickListener: () -> Unit = {
        val intent = Intent(this.context, DetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupRecyclerView()
        scheduleCall()
        launch(Dispatchers.IO) { fetchHeight() }
        observeViewModel()
    }

    private fun scheduleCall() {
        launch(Dispatchers.IO) {
            FetchHeightJobService.scheduleJob(activity!!.applicationContext)
        }
    }

    private fun setupRecyclerView() {
        recyclerView.setupVerticalAdapter(adapter)
    }

    private suspend fun fetchHeight() {
        while (true) {
            viewModel.getPost()
            delay(5000)
        }
    }

    private fun observeViewModel() {
        viewModel.post.observe(this, Observer {
            posts.get(0).run {
                height = it.height
            }
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}