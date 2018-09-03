package com.firebaseapp.mavenuptodate.mavenme.settings

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract

interface SettingsContract {

    interface View : BaseContract.View {
        fun setupSettings(settings: HashMap<String, Boolean>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadSettings()
        fun saveSettings(option: String, value: Boolean)
    }

}