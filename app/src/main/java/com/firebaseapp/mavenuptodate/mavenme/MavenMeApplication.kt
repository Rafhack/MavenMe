package com.firebaseapp.mavenuptodate.mavenme

import android.app.Application
import com.google.firebase.auth.FirebaseUser

class MavenMeApplication : Application() {

    companion object {
        lateinit var application: Application
        var user: FirebaseUser? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}