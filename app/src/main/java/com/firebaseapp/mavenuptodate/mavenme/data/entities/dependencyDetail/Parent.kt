package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class Parent @JvmOverloads constructor(
        @field:Element var groupId: String = "",
        @field:Element var artifactId: String = "",
        @field:Element var version: String = ""
)