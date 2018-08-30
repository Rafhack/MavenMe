package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.UserAuthInteractor


class MyDependenciesPresenter : MyDependenciesContract.Presenter {

    private lateinit var view: MyDependenciesContract.View
    private val userAuthInteractor = UserAuthInteractor()

    override fun loadMyDependencies() {
        view.setProgress(true)
        view.showDependencies(arrayListOf())
        view.setProgress(false)
    }

    override fun authUser() {
        val authenticated = userAuthInteractor.authUser(view as AppCompatActivity)
        if (authenticated) loadMyDependencies()
    }

    override fun googleAuthResult(data: Intent?) {
        view.setProgress(true)
        userAuthInteractor.googleAuthResult(view as AppCompatActivity, data) { success ->
            if (success) loadMyDependencies() else view.setProgress(false)
        }
    }

    override fun attach(view: MyDependenciesContract.View) {
        this.view = view
    }

}