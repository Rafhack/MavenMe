package com.firebaseapp.mavenuptodate.mavenme.data.entities

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class MavenSearchArtifact : Dependency() {
    @SerializedName("a")
    var artifact: String = ""
    var repositoryId: String = ""
    @SerializedName("p")
    var packaging: String = ""
    @SerializedName("timestamp")
    var timeStamp: Long = 0L
    var versionCount: Int = 0
}