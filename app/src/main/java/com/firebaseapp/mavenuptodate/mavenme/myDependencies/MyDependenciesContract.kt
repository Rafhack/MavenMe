package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract
import com.firebaseapp.mavenuptodate.mavenme.data.entities.MavenSearchAtifact

interface MyDependenciesContract {

    interface View : BaseContract.View {
        fun showDependencies(dependencies: ArrayList<MavenSearchAtifact>)
        fun showSuggestions(suggestions: ArrayList<String>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadMyDependencies()
        fun searchMaven(search: String)
    }

}