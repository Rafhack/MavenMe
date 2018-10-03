package com.firebaseapp.mavenuptodate.mavenme.notificationService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.evernote.android.job.Job
import com.firebase.jobdispatcher.JobService
import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MavenSearchInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MyDependenciesInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.myDependencies.MyDependenciesActivity
import com.firebaseapp.mavenuptodate.mavenme.settings.OPT_CHECK_NEVER
import com.firebaseapp.mavenuptodate.mavenme.settings.PREF_CHECK_INTERVAL
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.schedulers.Schedulers

const val TAG = "MavenMeNotification"

class EvernoteNotificationService : Job() {

    private val myDependenciesInteractor = MyDependenciesInteractor()

    override fun onRunJob(params: Params): Result {
        Log.d(TAG, "EvernoteJob Started")
        getMyDependencies()
        return Result.SUCCESS
    }

    private fun getMyDependencies() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            MavenMeApplication.user = auth.currentUser
            myDependenciesInteractor.loadUpToDateOnly { checkDependenciesUpToDate(it) }
        } else {
            Log.d(TAG, "EvernoteJob failed!")
        }
    }

    private fun checkDependenciesUpToDate(myDependencies: ArrayList<Dependency>) {
        val searchInteractor = MavenSearchInteractor()
        searchInteractor.checkOutOfDate(myDependencies)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        myDependenciesInteractor.addMultipleToCollection(it)
                        showNotification(it)
                    }
                    Log.d(TAG, "EvernoteJob success!")
                }, {
                    Log.d(TAG, "EvernoteJob failed!")
                })
    }

    private fun showNotification(dependencies: ArrayList<Dependency>) {
        val checkOption = context.getSharedPreferences("mavenme", Context.MODE_PRIVATE)
                .getInt(PREF_CHECK_INTERVAL, 0)
        if (checkOption == OPT_CHECK_NEVER) return

        val intent = Intent(context, MyDependenciesActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val bigText = NotificationCompat.BigTextStyle()

        val title = context.getString(R.string.notification_title)
        val body = if (dependencies.size > 1)
            context.resources.getQuantityText(R.plurals.notification_big_text_multiple,
                    dependencies.size - 1).toString().format(dependencies[0].artifact, (dependencies.size - 1).toString())
        else dependencies[0].artifact

        bigText.setBigContentTitle(title)
        bigText.bigText(body)
        bigText.setSummaryText(context.getString(R.string.notification_summary))

        val builder = NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setStyle(bigText)

        val manager = context.getSystemService(JobService.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(context.getString(R.string.default_notification_channel_id),
                    context.getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        manager.notify(93285, builder.build())
    }

}