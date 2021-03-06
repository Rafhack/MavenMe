package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.domain.RC_SIGN_IN
import com.firebaseapp.mavenuptodate.mavenme.dependencyDetail.DependencyDetailActivity
import com.firebaseapp.mavenuptodate.mavenme.mavenSearch.MavenSearchActivity
import com.firebaseapp.mavenuptodate.mavenme.mavenSearch.MavenSearchAdapter
import com.firebaseapp.mavenuptodate.mavenme.settings.CONTENT_CHANGED
import com.firebaseapp.mavenuptodate.mavenme.settings.RC_SETTINGS
import com.firebaseapp.mavenuptodate.mavenme.settings.SettingsActivity
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
        presenter.authUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_dependencies_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.my_dependencies_menu_settings -> {
                startActivityForResult(Intent(this, SettingsActivity::class.java), RC_SETTINGS)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> presenter.googleAuthResult(data)
            RC_SETTINGS -> if (data?.getBooleanExtra(CONTENT_CHANGED, false)!!) recreate()
        }
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
        rcvDependencies.adapter?.notifyDataSetChanged()
    }

    override fun showLoginButton() {
        tvwLoginToLoad.visibility = VISIBLE
        signInButton.visibility = VISIBLE
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDependencies() {
        rcvDependencies.background = null
        signInButton.visibility = GONE
        if (srlUpdateMyDependencies.isRefreshing) srlUpdateMyDependencies.isRefreshing = false
        if (presenter.myDependencies.isEmpty()) {
            tvwLoginToLoad.setText(R.string.you_dont_have_dependencies)
        } else {
            presenter.checkDependenciesUptoDate()
            tvwLoginToLoad.visibility = GONE
            rcvDependencies.adapter = MavenSearchAdapter(presenter.myDependencies) {
                startActivity(Intent(this, DependencyDetailActivity::class.java).apply {
                    putExtra("dependency", Parcels.wrap(it))
                    putExtra("fromCollection", true)
                })
            }
        }
    }
}