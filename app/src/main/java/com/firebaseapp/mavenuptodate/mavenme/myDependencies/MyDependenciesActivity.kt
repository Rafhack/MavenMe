package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.RC_SIGN_IN
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.mavenSearch.MavenSearchActivity
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_my_dependencies.*

class MyDependenciesActivity : BaseProgressActivity(), MyDependenciesContract.View {

    private val presenter = MyDependenciesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dependencies)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_activity_my_dependencies)
        setupView()

        presenter.attach(this)
        presenter.authUser()
    }

    private fun setupView() {
        signInButton.setOnClickListener { presenter.authUser() }

        fab.setOnClickListener { startActivity(Intent(this, MavenSearchActivity::class.java)) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) presenter.googleAuthResult(data)
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