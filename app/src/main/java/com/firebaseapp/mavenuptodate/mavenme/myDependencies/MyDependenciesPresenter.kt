package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MavenSearchInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MyDependenciesInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.UserAuthInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyDependenciesPresenter : MyDependenciesContract.Presenter {

    private lateinit var view: MyDependenciesContract.View
    private val userAuthInteractor = UserAuthInteractor()
    private val searchInteractor = MavenSearchInteractor()
    private val myDependenciesInteractor = MyDependenciesInteractor()
    val myDependencies = arrayListOf<Dependency>()

    override fun loadMyDependencies() {
        view.setProgress(true)
        myDependenciesInteractor.loadCollection {
            myDependencies.clear()
            myDependencies.addAll(it)
            view.showDependencies()
            view.setProgress(false)
        }
    }

    override fun checkDependenciesUptoDate() {
        view.setToolbarProgress(true)
        searchInteractor.checkOutOfDate(myDependencies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.updateDependencyStatus()
                    view.setToolbarProgress(false)
                }, {
                    view.setToolbarProgress(false)
                })
    }

    override fun authUser() {
        val authenticated = userAuthInteractor.authUser(view as AppCompatActivity)
        if (authenticated) loadMyDependencies() else view.showLoginButton()
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