package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency

interface MyDependenciesContract {

    interface View : BaseContract.View {
        fun showDependencies(dependencies: ArrayList<Dependency>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadMyDependencies()
        fun authUser()
        fun googleAuthResult(data: Intent?)
    }

}