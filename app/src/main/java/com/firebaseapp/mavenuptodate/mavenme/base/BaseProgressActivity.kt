package com.firebaseapp.mavenuptodate.mavenme.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.settings.PREF_DARK_THEME
import com.firebaseapp.mavenuptodate.mavenme.settings.SettingsPresenter
import kotlinx.android.synthetic.main.activity_base_progress.*

@SuppressLint("Registered")
open class BaseProgressActivity : AppCompatActivity() {

    //region Fields
    private lateinit var frmRoot: CoordinatorLayout
    private lateinit var frmProgress: FrameLayout
    private val sp get() = getSharedPreferences("mavenme", Context.MODE_PRIVATE)
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (sp.getBoolean(PREF_DARK_THEME, false)) R.style.AppTheme_Dark else R.style.AppTheme)
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base_progress)

        frmRoot = findViewById(R.id.activity_base_progress_frm_root)
        frmProgress = findViewById(R.id.activity_base_progress_frm_progress)
    }

    override fun setContentView(layoutResID: Int) {
        val frmContent: FrameLayout = frmRoot.findViewById(R.id.activity_base_progress_frm_content)
        layoutInflater.inflate(layoutResID, frmContent, true)
        super.setContentView(frmRoot)
    }
    //endregion

    //region Protected functions
    protected fun showProgress() {
        frmProgress.visibility = VISIBLE
        fab.isEnabled = false
    }

    protected fun hideProgress() {
        frmProgress.visibility = GONE
        fab.isEnabled = true
    }
    //endregion

}