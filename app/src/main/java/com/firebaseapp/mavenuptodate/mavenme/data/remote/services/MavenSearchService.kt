package com.firebaseapp.mavenuptodate.mavenme.data.remote.services

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MavenSearchService {

    @GET("solrsearch/select")
    fun mavenSearch(@Query("q") search: String, @Query("wt") returnType: String = "json"): Single<JsonObject>

}