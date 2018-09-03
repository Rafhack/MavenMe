package com.firebaseapp.mavenuptodate.mavenme.data.entities

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
open class Dependency {
    @SerializedName("id")
    var artifactId: String = ""
    @SerializedName("g")
    var group: String = ""
    var displayName: String = ""
    @SerializedName("latestVersion")
    var currentVersion: String = ""
    var upToDate: Boolean = false
    @SerializedName("timestamp")
    var timeStamp: Long = 0L
    var versionCount: Int = 0
}