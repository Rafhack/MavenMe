package com.firebaseapp.mavenuptodate.mavenme.data.entities

data class Dependency(
        var artifactId: String = "",
        var displayName: String = "",
        var currentVersion: String = "",
        var upToDate: Boolean = false
)