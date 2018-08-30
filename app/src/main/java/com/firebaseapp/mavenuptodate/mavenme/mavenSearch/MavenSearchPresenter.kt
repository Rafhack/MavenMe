package com.firebaseapp.mavenuptodate.mavenme.mavenSearch

import com.firebaseapp.mavenuptodate.mavenme.data.domain.MavenSearchInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.MavenSearchArtifact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MavenSearchPresenter : MavenSearchContract.Presenter {

    val searchResults: ArrayList<MavenSearchArtifact> = arrayListOf()
    private lateinit var view: MavenSearchContract.View
    private val searchInteractor = MavenSearchInteractor()

    override fun mavenSearch(search: String) {
        if (search.isEmpty()) {
            searchResults.clear()
            view.showSearchResult()
            return
        }
        view.setProgress(true)
        searchInteractor.mavenSearch(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    searchResults.clear()
                    if (it.response.docs.isEmpty() && it.suggestions.isNotEmpty())
                        view.showSuggestions(it.suggestions)
                    else {
                        searchResults.addAll(it.response.docs)
                        view.showSearchResult()
                    }
                    view.setProgress(false)
                }, {
                    view.setProgress(false)
                })
    }

    override fun attach(view: MavenSearchContract.View) {
        this.view = view
    }
}