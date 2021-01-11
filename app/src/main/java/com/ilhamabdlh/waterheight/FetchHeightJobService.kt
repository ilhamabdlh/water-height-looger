package com.ilhamabdlh.waterheight

import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.ilhamabdlh.waterheight.ui.Repository
import com.ilhamabdlh.waterheight.ui.notification.NotificationActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FetchHeightJobService : JobService(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.e("Job", "stopped")
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        launch(Dispatchers.IO) {
            while (true) {
                Log.e("Job", "started")
                val resultAsync = async { Repository.getHeight() }
                val result = resultAsync.await()
                if (result.isSuccessful) {
                    if (result.body()?.height ?: 0 > 68) {
                        Log.e("Job", "notification called")
                        showNotification()
                    }
                }
                delay(10000L)
            }
        }
        return true
    }

    private fun showNotification() {
        val intent = Intent(this, NotificationActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, "channel")
            .setSmallIcon(R.drawable.ic_warning)
            .setContentTitle("Peringatan peningkatan muka air")
            .setContentText("Pos Fakultas Teknik")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setFullScreenIntent(pendingIntent, true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    companion object {
        fun scheduleJob(context: Context) {
            val component = ComponentName(context, FetchHeightJobService::class.java)
            val builder = JobInfo.Builder(0, component)
                .setMinimumLatency(10 * 1000)
                .setOverrideDeadline(15 * 1000)
                .build()
            val scheduler = ContextCompat.getSystemService(context, JobScheduler::class.java)
            scheduler!!.schedule(builder)
        }
    }
}