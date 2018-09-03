package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.CardView
import android.text.Html
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_dependency_detail.*
import kotlinx.android.synthetic.main.dependencies_property_open.*
import kotlinx.android.synthetic.main.developers_property_open.*
import kotlinx.android.synthetic.main.licenses_property_open.*
import org.parceler.Parcels

class DependencyDetailActivity : BaseProgressActivity(), DependencyDetailContract.View {

    private lateinit var dependency: Dependency
    private val presenter = DependencyDetailPresenter()
    private var isPropertyOpen = false
    private var fromCollection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_detail)

        dependency = Parcels.unwrap<Dependency>(intent.getParcelableExtra("dependency"))
        fromCollection = intent.getBooleanExtra("fromCollection", false)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        if (fromCollection) menu?.removeItem(R.id.menu_item_add_to_collection)
        else menu?.removeItem(R.id.menu_item_remove_from_collection)
        if (dependency.upToDate) menu?.removeItem(R.id.menu_item_update)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_item_add_to_collection -> presenter.addToCollection(dependency)
            R.id.menu_item_remove_from_collection -> presenter.removeFromCollections(dependency)
            R.id.menu_item_update -> presenter.updateDependency(dependency)
        }

        return super.onOptionsItemSelected(item)
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
        tvwDescription.setOnClickListener {
            tvwDescription.maxLines = if (tvwDescription.maxLines == -1) 5 else -1
        }
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
            rcvLicensesList.adapter = LicensesAdapter(project.licenses)
            rcvDependenciesList.adapter = DependencyAdapter(project.dependencies)
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
        tvwDescription.maxLines = 5
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

    override fun showAddToCollectionErrorMessage() {
        Toast.makeText(this, R.string.add_error_message, Toast.LENGTH_LONG).show()
    }

    override fun showAddToCollectionSuccessMessage() {
        fromCollection = true
        invalidateOptionsMenu()
        Toast.makeText(this, R.string.add_success_message, Toast.LENGTH_LONG).show()
    }

    override fun showRemoveFromCollectionErrorMessage() {
        Toast.makeText(this, R.string.remove_error_message, Toast.LENGTH_LONG).show()
    }

    override fun showRemoveFromCollectionSuccessMessage() {
        Toast.makeText(this, R.string.remove_success_message, Toast.LENGTH_LONG).show()
        fromCollection = false
        invalidateOptionsMenu()
    }

    override fun showUpdateSuccess() {
        Toast.makeText(this, R.string.update_success_message, Toast.LENGTH_LONG).show()
    }

    override fun showUpdateError() {
        Toast.makeText(this, R.string.update_error_message, Toast.LENGTH_LONG).show()
    }
}
