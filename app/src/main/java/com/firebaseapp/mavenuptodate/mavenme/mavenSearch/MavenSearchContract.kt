package com.firebaseapp.mavenuptodate.mavenme.mavenSearch

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract

interface MavenSearchContract {

    interface View : BaseContract.View {
        fun showSearchResult()
        fun showSuggestions(suggestions: ArrayList<String>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun mavenSearch(search: String)
    }

}