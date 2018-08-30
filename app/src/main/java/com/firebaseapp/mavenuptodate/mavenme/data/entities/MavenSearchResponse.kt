package com.firebaseapp.mavenuptodate.mavenme.data.entities

data class MavenSearchResponse(
        var numFound: Int = 0,
        var start: Int = 0,
        var docs: ArrayList<MavenSearchAtifact> = arrayListOf()
)