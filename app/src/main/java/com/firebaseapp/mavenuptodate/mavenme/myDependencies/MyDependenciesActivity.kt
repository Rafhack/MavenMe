package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.os.Bundle
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.entities.MavenSearchAtifact
import com.firebaseapp.mavenuptodate.mavenme.domain.UserAuthInteractor
import kotlinx.android.synthetic.main.activity_base_progress.*

class MyDependenciesActivity : BaseProgressActivity(), MyDependenciesContract.View {

    private val presenter = MyDependenciesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dependencies)
        setSupportActionBar(toolbar)
        presenter.attach(this)
        presenter.authUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UserAuthInteractor.rcSignIn) presenter.googleAuthResult(data)
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDependencies(dependencies: ArrayList<MavenSearchAtifact>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuggestions(suggestions: ArrayList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}