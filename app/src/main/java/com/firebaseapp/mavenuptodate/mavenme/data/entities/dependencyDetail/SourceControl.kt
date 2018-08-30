package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class SourceControl @JvmOverloads constructor(
        @field:Element var connection: String = "",
        @field:Element var developerConnection: String = "",
        @field:Element var url: String = ""
)