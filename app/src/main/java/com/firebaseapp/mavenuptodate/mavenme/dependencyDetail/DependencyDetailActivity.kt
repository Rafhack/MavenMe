package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.CardView
import android.text.Html
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_dependency_deatail.*
import kotlinx.android.synthetic.main.developers_property_open.*
import org.parceler.Parcels

class DependencyDetailActivity : BaseProgressActivity(), DependencyDetailContract.View {

    private val presenter = DependencyDetailPresenter()
    private var isPropertyOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_deatail)

        val dependency = Parcels.unwrap<Dependency>(intent.getParcelableExtra("dependency"))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupView()
        presenter.attach(this)
        presenter.loadDetails(dependency)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (isPropertyOpen) closePropertyCard() else super.onBackPressed()
    }

    private fun setupView() {

        val layoutParams = (fab.layoutParams as CoordinatorLayout.LayoutParams)
        val listener = { v: View -> openPropertyCard(v as CardView) }

        fab.visibility = GONE
        fab.setImageResource(R.drawable.ic_close_light)
        layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.fab_in_card_margin)
        layoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.fab_in_card_margin)

        fab.setOnClickListener { closePropertyCard() }
        cdvDevelopers.setOnClickListener(listener)
        cdvLicences.setOnClickListener(listener)
        cdvDependencies.setOnClickListener(listener)
    }

    private fun setupPOMDetails(project: Project) {
        with(project) {
            if (description.isNotEmpty())
                tvwDescription.text = description
            else
                tvwDescription.visibility = GONE

            if (url.isNotEmpty())
                tvwUrl.text = url
            else
                tvwUrl.visibility = GONE

            var versionString = ""
            if (version.isNotEmpty())
                versionString = version
            else if (parent != null)
                versionString = parent?.version!!
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                tvwVersion.text = Html.fromHtml(getString(R.string.latest_version_detail, versionString), Html.FROM_HTML_MODE_LEGACY)
            else
                tvwVersion.text = Html.fromHtml(getString(R.string.latest_version_detail, versionString))

            tvwDevelopersCount.text = developers.size.toString()
            tvwDevelopersTitle.text = resources.getQuantityText(R.plurals.developer, developers.size)
            cdvDevelopers.isEnabled = developers.isNotEmpty()

            tvwLicensesCount.text = licenses.size.toString()
            tvwLicensesTitle.text = resources.getQuantityText(R.plurals.license, licenses.size)
            cdvLicences.isEnabled = licenses.isNotEmpty()

            tvwDependenciesCount.text = dependencies.size.toString()
            tvwDependenciesTitle.text = resources.getQuantityText(R.plurals.dependency, dependencies.size)
            cdvDependencies.isEnabled = dependencies.isNotEmpty()

            rcvDevelopersList.adapter = DevelopersAdapter(project.developers)
        }
    }

    private fun closePropertyCard() {
        arrayListOf<View>(cdvDevelopers, cdvLicences, cdvDependencies).forEach {
            val layoutParams = (it.layoutParams as ConstraintLayout.LayoutParams)
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.info_card_height)
            it.visibility = VISIBLE
            it.isClickable = true
            it.isFocusable = true
            (it as ViewGroup).getChildAt(0).visibility = VISIBLE
            it.getChildAt(1).visibility = GONE
        }
        fab.visibility = GONE
        isPropertyOpen = false
    }

    private fun openPropertyCard(cardView: CardView) {
        val layoutParams = (cardView.layoutParams as ConstraintLayout.LayoutParams)
        cdvDevelopers.visibility = if (cardView == cdvDevelopers) VISIBLE else GONE
        cdvLicences.visibility = if (cardView == cdvLicences) VISIBLE else GONE
        cdvDependencies.visibility = if (cardView == cdvDependencies) VISIBLE else GONE
        cardView.isClickable = false
        cardView.isFocusable = false
        cardView.getChildAt(1).visibility = VISIBLE
        cardView.getChildAt(0).visibility = GONE
        layoutParams.height = 0
        fab.visibility = VISIBLE
        isPropertyOpen = true
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDetails(project: Project) {
        supportActionBar?.title = project.name
        setupPOMDetails(project)
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show()
        finish()
    }
}
