package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract

interface MyDependenciesContract {

    interface View : BaseContract.View {
        fun showDependencies()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadDependencies()
    }

}