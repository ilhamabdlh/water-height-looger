package com.ilhamabdlh.waterheight.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilhamabdlh.waterheight.R
import com.ilhamabdlh.waterheight.ui.main.MainFragment

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NotificationFragment.newInstance())
                .commitNow()
        }
    }
}