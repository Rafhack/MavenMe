package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class SourceControl @JvmOverloads constructor(
        @field:Element(required = false) var connection: String = "",
        @field:Element(required = false) var developerConnection: String = "",
        @field:Element(required = false) var url: String = ""
)