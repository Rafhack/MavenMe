package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.DependencyDetailInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MavenSearchInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.MyDependenciesInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.domain.UserAuthInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DependencyDetailPresenter : DependencyDetailContract.Presenter {

    private lateinit var view: DependencyDetailContract.View
    private val detailInteractor = DependencyDetailInteractor()
    private val userAuthInteractor = UserAuthInteractor()
    private val myDependenciesInteractor = MyDependenciesInteractor()
    private val searchInteractor = MavenSearchInteractor()
    private var pendingDependency: Dependency? = null

    override fun addToCollection(dependency: Dependency) {
        if (userAuthInteractor.authUser(view as AppCompatActivity)) {
            view.setProgress(true)
            myDependenciesInteractor.addToCollection(dependency) { success ->
                view.setProgress(false)
                if (success)
                    view.showAddToCollectionSuccessMessage()
                else
                    view.showAddToCollectionErrorMessage()
            }
        } else {
            pendingDependency = dependency
        }
    }

    override fun googleAuthResult(data: Intent?) {
        view.setProgress(true)
        userAuthInteractor.googleAuthResult(view as AppCompatActivity, data) { success ->
            if (success && pendingDependency != null) addToCollection(pendingDependency!!)
            else view.setProgress(false)
        }
    }

    override fun updateDependency(dependency: Dependency) {
        view.setProgress(true)
        searchInteractor.getLatestVersion(dependency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    myDependenciesInteractor.addToCollection(it) { success ->
                        if (success) {
                            view.showUpdateSuccess()
                            loadDetails(it)
                        } else {
                            view.setProgress(false)
                            view.showUpdateError()
                        }
                    }
                }, {
                    view.showUpdateError()
                    view.setProgress(false)
                })
    }

    override fun removeFromCollections(dependency: Dependency) {
        view.setProgress(true)
        myDependenciesInteractor.removeFromCollections(dependency) { success ->
            view.setProgress(false)
            if (success)
                view.showRemoveFromCollectionSuccessMessage()
            else
                view.showRemoveFromCollectionErrorMessage()
        }
    }

    override fun loadDetails(dependency: Dependency) {
        view.setProgress(true)
        detailInteractor.loadDetails(dependency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.setProgress(false)
                    view.showDetails(it)
                }, {
                    view.showErrorMessage()
                    view.setProgress(false)
                })
    }

    override fun attach(view: DependencyDetailContract.View) {
        this.view = view
    }
}