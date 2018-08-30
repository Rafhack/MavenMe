package com.firebaseapp.mavenuptodate.mavenme.data.remote.services

import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Project
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DependencyDetailService {

    @GET("remotecontent")
    fun loadDetails(@Query("filepath") path: String): Single<Project>
}