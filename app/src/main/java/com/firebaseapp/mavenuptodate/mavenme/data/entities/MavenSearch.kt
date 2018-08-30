package com.firebaseapp.mavenuptodate.mavenme.data.entities

data class MavenSearch(
        var response: MavenSearchResponse = MavenSearchResponse(),
        var suggestions: ArrayList<String> = arrayListOf()
)