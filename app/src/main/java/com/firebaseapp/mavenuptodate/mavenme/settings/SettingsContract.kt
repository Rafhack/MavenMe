package com.firebaseapp.mavenuptodate.mavenme.settings

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract

interface SettingsContract {

    interface View : BaseContract.View {
        fun setupSettings(settings: HashMap<String, Any>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadSettings()
        fun saveDarkThemeSettings(value: Boolean)
        fun saveNotificationSettings(value: Int)
    }

}