package com.firebaseapp.mavenuptodate.mavenme

import android.app.Application
import android.content.Context
import android.util.Log
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.firebaseapp.mavenuptodate.mavenme.notificationService.NotificationServiceCreator
import com.firebaseapp.mavenuptodate.mavenme.notificationService.TAG
import com.firebaseapp.mavenuptodate.mavenme.settings.OPT_CHECK_EVERY_24_HOUR
import com.firebaseapp.mavenuptodate.mavenme.settings.OPT_CHECK_EVERY_HOUR
import com.firebaseapp.mavenuptodate.mavenme.settings.PREF_CHECK_INTERVAL
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.MINUTES


class MavenMeApplication : Application() {

    companion object {
        lateinit var application: Application
        var user: FirebaseUser? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        JobManager.create(this).addJobCreator(NotificationServiceCreator())
        scheduleJob()
    }

    fun scheduleJob() {
        val sp = getSharedPreferences("mavenme", Context.MODE_PRIVATE)
        val exWindowStart = when (sp.getInt(PREF_CHECK_INTERVAL, 0)) {
            OPT_CHECK_EVERY_HOUR -> MINUTES.toMillis(60)
            OPT_CHECK_EVERY_24_HOUR -> DAYS.toMillis(1)
            else -> 0
        }
        val exWindowEnd = MINUTES.toMillis(5)

        if (exWindowStart == 0L) return

        JobRequest.Builder(TAG)
                .setPeriodic(exWindowStart, exWindowEnd)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .build()
                .schedule()
        Log.d(TAG, "EvernoteJob scheduled!")
    }

}