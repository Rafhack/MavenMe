package com.firebaseapp.mavenuptodate.mavenme.data.domain

import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.firebaseapp.mavenuptodate.mavenme.data.remote.FirestoreGenerator


class MyDependenciesInteractor {

    private val db
        get() = FirestoreGenerator.getFirestore()

    fun addToCollection(dependency: Dependency, callback: ((Boolean) -> Unit)) {
        db.document("users/${MavenMeApplication.user?.uid}/dependencies/${dependency.artifactId}")
                .set(dependency)
                .addOnCompleteListener { callback.invoke(it.isSuccessful) }
    }

    fun removeFromCollections(dependency: Dependency, callback: ((Boolean) -> Unit)) {
        db.document("users/${MavenMeApplication.user?.uid}/dependencies/${dependency.artifactId}")
                .delete()
                .addOnCompleteListener { callback.invoke(it.isSuccessful) }
    }

    fun loadCollection(callback: (ArrayList<Dependency>) -> Unit) {
        db.collection("users/${MavenMeApplication.user?.uid}/dependencies")
                .addSnapshotListener { result, _ ->
                    val myDependencies = arrayListOf<Dependency>()
                    result?.forEach { doc ->
                        myDependencies.add(doc.toObject(Dependency::class.java))
                    }
                    callback.invoke(myDependencies)
                }
    }

}