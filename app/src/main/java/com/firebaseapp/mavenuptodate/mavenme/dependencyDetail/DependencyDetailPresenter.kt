package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import com.firebaseapp.mavenuptodate.mavenme.data.domain.DependencyDetailInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DependencyDetailPresenter : DependencyDetailContract.Presenter {

    private lateinit var view: DependencyDetailContract.View
    private val detailInteractor = DependencyDetailInteractor()

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