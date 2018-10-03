package com.firebaseapp.mavenuptodate.mavenme.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_settings.*

const val RC_SETTINGS = 0x34
const val CONTENT_CHANGED = "contentChanged"

class SettingsActivity : BaseProgressActivity(), SettingsContract.View {

    private val presenter = SettingsPresenter()
    private var changed = false
    private var checkedItem = 0
    private lateinit var notificationOptions: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        notificationOptions = arrayOf(
                getString(R.string.notification_interval_every_hour),
                getString(R.string.notification_interval_once_a_day),
                getString(R.string.notification_interval_never)
        )

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

    override fun setupSettings(settings: HashMap<String, Any>) {
        swtDarkTheme.isChecked = settings[PREF_DARK_THEME] as Boolean
        checkedItem = settings[PREF_CHECK_INTERVAL] as Int
        tvwNotificationsInterval.text = notificationOptions[checkedItem]
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
        fab.hide()

        ctlDarkTheme.setOnClickListener { swtDarkTheme.toggle() }
        swtDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            changed = true
            presenter.saveDarkThemeSettings(isChecked)
            recreate()
        }

        ctlCheckInterval.setOnClickListener { showNotificationDialog() }
    }

    private fun showNotificationDialog() {

        AlertDialog.Builder(this)
                .setTitle(R.string.notification_interval)
                .setSingleChoiceItems(notificationOptions, checkedItem) { dialog, choice ->
                    checkedItem = choice
                    tvwNotificationsInterval.text = notificationOptions[choice]
                    presenter.saveNotificationSettings(choice)
                    (application as MavenMeApplication).scheduleJob()
                    dialog.dismiss()
                }.create().show()
    }
}
