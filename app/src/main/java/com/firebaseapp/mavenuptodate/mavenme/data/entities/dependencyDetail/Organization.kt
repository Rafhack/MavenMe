package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element

data class Organization @JvmOverloads constructor(
        @field:Element var name: String = "",
        @field:Element var url: String = ""
)