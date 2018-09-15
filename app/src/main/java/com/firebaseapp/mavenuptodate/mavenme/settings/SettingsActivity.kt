package com.firebaseapp.mavenuptodate.mavenme.settings

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_settings.*

const val RC_SETTINGS = 0x34
const val CONTENT_CHANGED = "contentChanged"

class SettingsActivity : BaseProgressActivity(), SettingsContract.View {

    private val presenter = SettingsPresenter()
    private var changed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.attach(this)
        presenter.loadSettings()
        changed = savedInstanceState?.getBoolean(CONTENT_CHANGED, false) ?: false

        setupView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(CONTENT_CHANGED, changed)
        super.onSaveInstanceState(outState)
    }

    override fun setProgress(active: Boolean) {}

    override fun setupSettings(settings: HashMap<String, Boolean>) {
        swtDarkTheme.isChecked = settings[PREF_DARK_THEME]!!
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent().apply { putExtra(CONTENT_CHANGED, changed) })
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupView() {
        fab.visibility = GONE
        tvwDarkTheme.setOnClickListener { swtDarkTheme.toggle() }
        swtDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            changed = true
            presenter.saveSettings(PREF_DARK_THEME, isChecked)
            recreate()
        }
    }
}
