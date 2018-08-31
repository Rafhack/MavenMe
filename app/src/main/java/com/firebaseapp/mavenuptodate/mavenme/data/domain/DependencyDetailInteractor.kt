package com.firebaseapp.mavenuptodate.mavenme.data.domain

import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project
import com.firebaseapp.mavenuptodate.mavenme.data.remote.ServiceGenerator
import com.firebaseapp.mavenuptodate.mavenme.data.remote.services.DependencyDetailService
import io.reactivex.Single

class DependencyDetailInteractor {

    private val service: DependencyDetailService
        get() = ServiceGenerator.createService(DependencyDetailService::class.java, xml = true)

    fun loadDetails(dependency: Dependency): Single<Project> {
        val name = dependency.artifactId.split(":").last()
        val version = dependency.currentVersion
        val path = dependency.group
                .replace('.', '/')
                .plus("/$name")
                .plus("/$version")
                .plus("/$name-$version.pom")
        return service.loadDetails(path)
    }

}