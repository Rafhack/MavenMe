package com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "project")
data class Project @JvmOverloads constructor(
        @field:Element(required = false) var groupId: String = "",
        @field:Element(required = false) var artifactId: String = "",
        @field:Element(required = false) var version: String = "",
        @field:Element(required = false) var name: String = "",
        @field:Element(required = false) var description: String = "",
        @field:Element(required = false) var url: String = "",
        @field:Element(required = false) var organization: Organization? = null,
        @field:Element(required = false) var parent: Parent? = null,
        @field:Element(required = false) var scm: SourceControl? = null,
        @field:ElementList(name = "dependencies", required = false) var dependencies: ArrayList<ProjectDependency> = arrayListOf(),
        @field:ElementList(name = "licenses", required = false) var licenses: ArrayList<License> = arrayListOf(),
        @field:ElementList(name = "developers", required = false) var developers: ArrayList<Developer> = arrayListOf()

)