package com.firebaseapp.mavenuptodate.mavenme.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE

const val PREF_DARK_THEME = "dark_theme"

class SettingsPresenter : SettingsContract.Presenter {

    private lateinit var view: SettingsContract.View
    private val sp
        get() = (view as Context).getSharedPreferences("mavenme", MODE_PRIVATE)

    override fun loadSettings() {
        val settings = hashMapOf<String, Boolean>()
        settings[PREF_DARK_THEME] = sp.getBoolean(PREF_DARK_THEME, false)
        view.setupSettings(settings)
    }

    override fun saveSettings(option: String, value: Boolean) {
        sp.edit().putBoolean(option, value).apply()
    }

    override fun attach(view: SettingsContract.View) {
        this.view = view
    }

}