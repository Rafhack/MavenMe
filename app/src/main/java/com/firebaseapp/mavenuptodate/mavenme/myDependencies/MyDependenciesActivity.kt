package com.firebaseapp.mavenuptodate.mavenme.myDependencies

import android.os.Bundle
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import kotlinx.android.synthetic.main.activity_base_progress.*

class MyDependenciesActivity : BaseProgressActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dependencies)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showProgress()
        }
    }

}