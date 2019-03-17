package com.firebaseapp.mavenuptodate.mavenme.privacyPolicy

import com.firebaseapp.mavenuptodate.mavenme.base.BaseContract

interface PrivacyPolicyContract {

    interface View: BaseContract.View {
        fun showPrivacyPolicy()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadPrivacyPolicy()
    }

}