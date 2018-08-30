package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class Developer @JvmOverloads constructor(
        @field:Element(required = false) var id: String = "",
        @field:Element(required = false) var name: String = "",
        @field:Element(required = false) var email: String = ""
)