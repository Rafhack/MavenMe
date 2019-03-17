package com.firebaseapp.mavenuptodate.mavenme.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE

const val PREF_DARK_THEME = "dark_theme"
const val PREF_CHECK_INTERVAL = "check_interval"

const val OPT_CHECK_EVERY_HOUR = 0
const val OPT_CHECK_EVERY_24_HOUR = 1
const val OPT_CHECK_NEVER = 2

class SettingsPresenter : SettingsContract.Presenter {

    private lateinit var view: SettingsContract.View
    private val sp
        get() = (view as Context).getSharedPreferences("mavenme", MODE_PRIVATE)

    override fun loadSettings() {
        val settings = hashMapOf<String, Any>()
        settings[PREF_DARK_THEME] = sp.getBoolean(PREF_DARK_THEME, false)
        settings[PREF_CHECK_INTERVAL] = sp.getInt(PREF_CHECK_INTERVAL, 0)
        view.setupSettings(settings)
    }

    override fun saveDarkThemeSettings(value: Boolean) {
        sp.edit().putBoolean(PREF_DARK_THEME, value).apply()
    }

    override fun saveNotificationSettings(value: Int) {
        sp.edit().putInt(PREF_CHECK_INTERVAL, value).apply()
    }

    override fun openPrivacyPolicy() {
        view.openPrivacyPolicyScreen()
    }

    override fun attach(view: SettingsContract.View) {
        this.view = view
    }

}