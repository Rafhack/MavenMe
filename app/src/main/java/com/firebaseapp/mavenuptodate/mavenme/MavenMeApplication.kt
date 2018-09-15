package com.firebaseapp.mavenuptodate.mavenme

import android.app.Application
import android.util.Log
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Lifetime.FOREVER
import com.firebase.jobdispatcher.RetryStrategy.DEFAULT_LINEAR
import com.firebase.jobdispatcher.Trigger
import com.firebaseapp.mavenuptodate.mavenme.notificationService.NotificationService
import com.firebaseapp.mavenuptodate.mavenme.notificationService.TAG
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.TimeUnit.MINUTES

class MavenMeApplication : Application() {

    companion object {
        lateinit var application: Application
        var user: FirebaseUser? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        scheduleJob()
    }

    fun scheduleJob() {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))

        val exWindowStart = MINUTES.toSeconds(50).toInt()
        val exWindowEnd = exWindowStart + MINUTES.toSeconds(10).toInt()

        val job = dispatcher.newJobBuilder()
                .setService(NotificationService::class.java)
                .setTag(TAG)
                .setLifetime(FOREVER)
                .setReplaceCurrent(true)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(exWindowStart, exWindowEnd))
                .setRetryStrategy(DEFAULT_LINEAR)
                .build()

        dispatcher.mustSchedule(job)
        Log.d(TAG, "Job scheduled!")
    }

}