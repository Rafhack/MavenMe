package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.os.Bundle
import android.util.Log
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project
import org.parceler.Parcels

class DependencyDetailActivity : BaseProgressActivity(), DependencyDetailContract.View {

    private val presenter = DependencyDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_deatail)
        val dependency = Parcels.unwrap<Dependency>(intent.getParcelableExtra("dependency"))
        presenter.attach(this)
        presenter.loadDetails(dependency)
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showDetails(project: Project) {
        Log.i("Wow", project.artifactId)
    }
}
