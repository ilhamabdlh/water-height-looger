package com.ilhamabdlh.waterheight.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ilhamabdlh.waterheight.R
import kotlinx.android.synthetic.main.notification_fragment.*

class NotificationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        icSlide.setOnClickListener { activity?.finish() }
        icClose.setOnClickListener { activity?.finish() }
    }

    companion object {
        fun newInstance() = NotificationFragment()
    }
}