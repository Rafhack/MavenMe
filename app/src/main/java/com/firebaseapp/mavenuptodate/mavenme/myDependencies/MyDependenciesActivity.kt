package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.UserAuthInteractor
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_my_dependencies.*

class MyDependenciesActivity : BaseProgressActivity(), MyDependenciesContract.View {

    private val presenter = MyDependenciesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dependencies)
        setSupportActionBar(toolbar)
        presenter.attach(this)
        presenter.authUser()
        setupView()
    }

    private fun setupView() {
        signInButton.setOnClickListener { presenter.authUser() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UserAuthInteractor.rcSignIn) presenter.googleAuthResult(data)
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDependencies(dependencies: ArrayList<Dependency>) {
        rcvDependencies.background = null
        signInButton.visibility = GONE
        if (dependencies.isEmpty())
            tvwLoginToLoad.setText(R.string.you_dont_have_dependencies)
        else
            tvwLoginToLoad.visibility = GONE
    }

}