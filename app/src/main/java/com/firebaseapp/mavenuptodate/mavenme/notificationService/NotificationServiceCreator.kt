package com.firebaseapp.mavenuptodate.mavenme.notificationService

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class NotificationServiceCreator : JobCreator {
    override fun create(tag: String): Job? = EvernoteNotificationService()
}