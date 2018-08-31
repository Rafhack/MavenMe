package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class Parent @JvmOverloads constructor(
        @field:Element(required = false) var groupId: String = "",
        @field:Element(required = false) var artifactId: String = "",
        @field:Element(required = false) var version: String = ""
)