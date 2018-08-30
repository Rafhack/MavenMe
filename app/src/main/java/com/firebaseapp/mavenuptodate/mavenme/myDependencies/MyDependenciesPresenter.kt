package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.mavenuptodate.mavenme.domain.UserAuthInteractor


class MyDependenciesPresenter : MyDependenciesContract.Presenter {

    private lateinit var view: MyDependenciesContract.View
    private val userAuthInteractor = UserAuthInteractor()

    override fun loadMyDependencies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchMaven(search: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun authUser() {
        userAuthInteractor.authUser(view as AppCompatActivity)
    }

    override fun googleAuthResult(data: Intent?) {
        view.setProgress(true)
        userAuthInteractor.googleAuthResult(view as AppCompatActivity, data) { view.setProgress(false) }
    }

    override fun attach(view: MyDependenciesContract.View) {
        this.view = view
    }

}