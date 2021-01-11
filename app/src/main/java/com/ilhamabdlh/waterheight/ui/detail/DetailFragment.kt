package com.ilhamabdlh.waterheight.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.ilhamabdlh.waterheight.R
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.icClose
import kotlinx.android.synthetic.main.item_post_each.view.*
import kotlinx.android.synthetic.main.notification_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random


class DetailFragment : Fragment(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
    val random = Random(100000)

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        setupChart()
        launch(Dispatchers.IO) { fetchHeight() }
        observeViewModel()
        icClose.setOnClickListener { activity?.finish() }
    }

    private suspend fun fetchHeight() {
        while (true) {
            viewModel.getHeight()
            delay(5000)
        }
    }

    private fun observeViewModel() {
        viewModel.height.observe(this, Observer {
            tvTitle.text = it.name
            tvAddress.text = it.address
            tvLastHeight.text = "${it.height} cm"
            if (it.height < 0) it.height = 0
            addEntryToChart(it.height.toFloat())
            Log.e("float", "${it.height.toFloat()}")
        })
    }

    private fun setupChart() {
        val emptyData = LineData().apply {
            setValueTextColor(ContextCompat.getColor(context!!, R.color.green))
        }

        chart.run {
            description.isEnabled = true
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setDrawGridBackground(false)
            setPinchZoom(true)
            setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            // set an empty data
            data = emptyData
        }

        // set axis
        chart.xAxis.apply {
            textColor = ContextCompat.getColor(chart.context, R.color.black)
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
            isEnabled = true
        }
        chart.axisLeft.apply {
            textColor = ContextCompat.getColor(chart.context, R.color.black)
            axisMinimum = 0f
            setDrawGridLines(true)
        }
        chart.axisRight.apply {
            isEnabled = false
        }
    }

    private fun addEntryToChart(height: Float) {
        val data = chart.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(set.entryCount.toFloat(), height), 0)
            data.notifyDataChanged()

            chart.notifyDataSetChanged()
            chart.setMaxVisibleValueCount(500)
            chart.moveViewToX(data.entryCount.toFloat());
        }
    }

    private fun createSet(): LineDataSet {
        val green = ContextCompat.getColor(chart.context, R.color.green)
        val black = ContextCompat.getColor(chart.context, R.color.black)

        val set = LineDataSet(null, "Ketinggian Air").apply {
            axisDependency = AxisDependency.LEFT
            color = green
            setCircleColor(green)
            lineWidth = 2f
            circleRadius = 4f
            fillAlpha = 65
            fillColor = green
            highLightColor = black
            valueTextColor = black
            valueTextSize = 9f
            setDrawValues(false)
        }
        return set
    }

    companion object {
        fun newInstance() = DetailFragment()
    }
}