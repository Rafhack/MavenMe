package com.firebaseapp.mavenuptodate.mavenme.privacyPolicy

import android.os.Bundle
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : BaseProgressActivity(), PrivacyPolicyContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        val presenter = PrivacyPolicyPresenter()
        presenter.attach(this)
        presenter.loadPrivacyPolicy()
        setupView()

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.privacy_policy)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupView() = fab.hide()

    override fun showPrivacyPolicy() {
        wbvPrivacyPolicy.loadUrl("file:///android_asset/privacy_policy.html")
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

}