package com.firebaseapp.mavenuptodate.mavenme.data.domain

import com.firebaseapp.mavenuptodate.mavenme.data.entities.MavenSearch
import com.firebaseapp.mavenuptodate.mavenme.data.remote.ServiceGenerator
import com.firebaseapp.mavenuptodate.mavenme.data.remote.services.MavenSearchService
import com.google.gson.GsonBuilder
import io.reactivex.Single

class MavenSearchInteractor {

    private val service: MavenSearchService
        get() = ServiceGenerator.createService(MavenSearchService::class.java)

    fun mavenSearch(search: String): Single<MavenSearch> {
        return service.mavenSearch(search).map { json ->
            val result = GsonBuilder().create().fromJson(json, MavenSearch::class.java)
            val suggestions = json["spellcheck"].asJsonObject["suggestions"].asJsonArray
            if (suggestions.size() < 1) return@map result
            result.suggestions.addAll(suggestions[1].asJsonObject["suggestion"].asJsonArray.map { it.asString })
            return@map result
        }
    }
}