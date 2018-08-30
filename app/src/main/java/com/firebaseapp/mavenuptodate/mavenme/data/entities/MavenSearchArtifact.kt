package com.firebaseapp.mavenuptodate.mavenme.data.entities

import com.google.gson.annotations.SerializedName

data class MavenSearchArtifact (
        var id: String = "",
        @SerializedName("g")
        var group: String = "",
        @SerializedName("a")
        var artifact: String = "",
        var latestVersion: String = "",
        var repositoryId: String = "",
        @SerializedName("p")
        var packaging: String = "",
        @SerializedName("timestamp")
        var timeStamp: Long = 0L,
        var versionCount: Int = 0
)