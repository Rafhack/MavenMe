package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency

interface MyDependenciesContract {

    interface View : BaseContract.View {
        fun showDependencies()
        fun showLoginButton()
        fun setToolbarProgress(active: Boolean)
        fun updateDependencyStatus()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun checkDependenciesUptoDate()
        fun loadMyDependencies()
        fun authUser()
        fun googleAuthResult(data: Intent?)
    }

}