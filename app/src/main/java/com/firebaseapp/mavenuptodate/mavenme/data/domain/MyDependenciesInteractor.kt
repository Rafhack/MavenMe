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
                .addOnCompleteListener { callback(it.isSuccessful) }
    }

    fun addMultipleToCollection(dependencies: ArrayList<Dependency>) {
        dependencies.forEach { dep ->
            db.document("users/${MavenMeApplication.user?.uid}/dependencies/${dep.artifactId}").set(dep)
        }
    }

    fun removeFromCollections(dependency: Dependency, callback: ((Boolean) -> Unit)) {
        db.document("users/${MavenMeApplication.user?.uid}/dependencies/${dependency.artifactId}")
                .delete()
                .addOnCompleteListener { callback(it.isSuccessful) }
    }

    fun loadCollection(callback: (ArrayList<Dependency>) -> Unit) {
        db.collection("users/${MavenMeApplication.user?.uid}/dependencies").get()
                .addOnCompleteListener {
                    val myDependencies = arrayListOf<Dependency>()
                    it.result.forEach { doc ->
                        myDependencies.add(doc.toObject(Dependency::class.java))
                    }
                    callback(myDependencies)
                }
    }

    fun loadUpToDateOnly(callback: (ArrayList<Dependency>) -> Unit) {
        db.collection("users/${MavenMeApplication.user?.uid}/dependencies")
                .whereEqualTo("upToDate", true).get()
                .addOnCompleteListener {
                    val myDependencies = arrayListOf<Dependency>()
                    it.result.forEach { doc ->
                        myDependencies.add(doc.toObject(Dependency::class.java))
                    }
                    callback(myDependencies)
                }
    }

}