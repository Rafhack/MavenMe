package com.firebaseapp.mavenuptodate.mavenme.privacyPolicy

class PrivacyPolicyPresenter: PrivacyPolicyContract.Presenter {

    private lateinit var view: PrivacyPolicyContract.View

    override fun loadPrivacyPolicy() {
        view.showPrivacyPolicy()
    }

    override fun attach(view: PrivacyPolicyContract.View) {
        this.view = view
    }

}