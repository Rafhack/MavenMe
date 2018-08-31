package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View.GONE
import android.widget.Toast
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_dependency_deatail.*
import org.parceler.Parcels

class DependencyDetailActivity : BaseProgressActivity(), DependencyDetailContract.View {

    private val presenter = DependencyDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_deatail)

        val dependency = Parcels.unwrap<Dependency>(intent.getParcelableExtra("dependency"))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        fab.visibility = GONE

        presenter.attach(this)
        presenter.loadDetails(dependency)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupView(project: Project) {
        if (project.description.isNotEmpty())
            tvwDescription.text = project.description
        else
            tvwDescription.visibility = GONE

        if (project.url.isNotEmpty())
            tvwUrl.text = project.url
        else
            tvwUrl.visibility = GONE

        var version = ""
        if (project.version.isNotEmpty())
            version = project.version
        else if (project.parent != null)
            version = project.parent?.version!!
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            tvwVersion.text = Html.fromHtml(getString(R.string.latest_version_detail, version), Html.FROM_HTML_MODE_LEGACY)
        else
            tvwVersion.text = Html.fromHtml(getString(R.string.latest_version_detail, version))
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDetails(project: Project) {
        supportActionBar?.title = project.name
        setupView(project)
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show()
        finish()
    }
}
