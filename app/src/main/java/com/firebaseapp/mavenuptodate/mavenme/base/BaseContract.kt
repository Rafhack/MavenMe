package com.firebaseapp.mavenuptodate.mavenme.base

interface BaseContract {

    interface View {
        fun setProgress(active: Boolean)
    }

    interface Presenter<T : View> {
        fun attach(view: T)
    }
}