package com.firebaseapp.mavenuptodate.mavenme.data.domain

import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import com.google.firebase.firestore.FirebaseFirestore


class MyDependenciesInteractor {

    private val db
        get() = FirebaseFirestore.getInstance()

    fun addToCollection(dependency: Dependency, callback: ((Boolean) -> Unit)) {
        db.document("users/${MavenMeApplication.user?.uid}/dependencies/${dependency.artifactId}")
                .set(dependency)
                .addOnCompleteListener { callback.invoke(it.isSuccessful) }
    }

    fun loadCollection(callback: (ArrayList<Dependency>) -> Unit) {
        val myDependencies = arrayListOf<Dependency>()
        db.collection("users/${MavenMeApplication.user?.uid}/dependencies").get()
                .addOnCompleteListener {
                    it.result.forEach { doc ->
                        myDependencies.add(doc.toObject(Dependency::class.java))
                    }
                    callback.invoke(myDependencies)
                }
    }

}