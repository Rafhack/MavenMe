package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.RC_SIGN_IN
import com.firebaseapp.mavenuptodate.mavenme.dependencyDetail.DependencyDetailActivity
import com.firebaseapp.mavenuptodate.mavenme.mavenSearch.MavenSearchActivity
import com.firebaseapp.mavenuptodate.mavenme.mavenSearch.MavenSearchAdapter
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_my_dependencies.*
import org.parceler.Parcels

class MyDependenciesActivity : BaseProgressActivity(), MyDependenciesContract.View {

    private val presenter = MyDependenciesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dependencies)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_activity_my_dependencies)
        setupView()

        presenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.authUser()
    }

    private fun setupView() {
        signInButton.setOnClickListener { presenter.authUser() }

        fab.setOnClickListener { startActivity(Intent(this, MavenSearchActivity::class.java)) }

        srlUpdateMyDependencies.setOnRefreshListener { presenter.authUser() }
    }

    override fun setToolbarProgress(active: Boolean) {
        toolbarProgressBar.visibility = if (active) VISIBLE else GONE
    }

    override fun updateDependencyStatus() {
        rcvDependencies.adapter.notifyDataSetChanged()
    }

    override fun showLoginButton() {
        signInButton.visibility = VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) presenter.googleAuthResult(data)
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDependencies() {
        rcvDependencies.background = null
        signInButton.visibility = GONE
        if (srlUpdateMyDependencies.isRefreshing) srlUpdateMyDependencies.isRefreshing = false
        if (presenter.myDependencies.isEmpty()) {
            tvwLoginToLoad.visibility = VISIBLE
            tvwLoginToLoad.setText(R.string.you_dont_have_dependencies)
        } else {
            presenter.checkDependenciesUptoDate()
            tvwLoginToLoad.visibility = GONE
            rcvDependencies.adapter = MavenSearchAdapter(presenter.myDependencies) {
                val intent = Intent(this, DependencyDetailActivity::class.java)
                intent.putExtra("dependency", Parcels.wrap(it))
                intent.putExtra("fromCollection", true)
                startActivity(intent)
            }
        }
    }
}