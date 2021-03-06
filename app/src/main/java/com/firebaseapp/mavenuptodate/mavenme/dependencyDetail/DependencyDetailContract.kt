package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.content.Intent
import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project

interface DependencyDetailContract {

    interface View : BaseContract.View {
        fun showDetails(project: Project)
        fun showErrorMessage()
        fun showAddToCollectionErrorMessage()
        fun showAddToCollectionSuccessMessage()
        fun showRemoveFromCollectionErrorMessage()
        fun showRemoveFromCollectionSuccessMessage()
        fun showUpdateSuccess()
        fun showUpdateError()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadDetails(dependency: Dependency)
        fun addToCollection(dependency: Dependency)
        fun googleAuthResult(data: Intent?)
        fun removeFromCollections(dependency: Dependency)
        fun updateDependency(dependency: Dependency)
    }

}