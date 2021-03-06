package com.ilhamabdlh.waterheight.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilhamabdlh.waterheight.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance())
                .commitNow()
        }
    }
}