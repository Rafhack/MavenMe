package com.firebaseapp.mavenuptodate.mavenme.notificationService

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MavenSearchInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MyDependenciesInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.schedulers.Schedulers

const val TAG = "MaveMeNotification2"

class NotificationService : com.firebase.jobdispatcher.JobService() {

    private val myDependenciesInteractor = MyDependenciesInteractor()

    override fun onStopJob(job: JobParameters?): Boolean = true

    override fun onStartJob(job: JobParameters?): Boolean {
        Log.d(TAG, "Job started!")
        getMyDependencies(job!!)
        return true
    }

    private fun getMyDependencies(job: JobParameters) {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            MavenMeApplication.user = auth.currentUser
            myDependenciesInteractor.loadUpToDateOnly { checkDependenciesUpToDate(it, job) }
        } else {
            Log.d(TAG, "Job failed!")
            jobFinished(job, false)
        }
    }

    private fun checkDependenciesUpToDate(myDependencies: ArrayList<Dependency>, job: JobParameters) {
        val searchInteractor = MavenSearchInteractor()

        searchInteractor.checkOutOfDate(myDependencies)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({
                    myDependenciesInteractor.addMultipleToCollection(it)
                    jobFinished(job, false)
                    Log.d(TAG, "Job success!")
                }, {
                    Log.d(TAG, "Job failed!")
                    jobFinished(job, true)
                })
    }

}