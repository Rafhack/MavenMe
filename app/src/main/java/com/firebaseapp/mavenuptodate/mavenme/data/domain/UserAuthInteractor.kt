package com.firebaseapp.mavenuptodate.mavenme.data.domain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebaseapp.mavenuptodate.mavenme.MavenMeApplication
import com.firebaseapp.mavenuptodate.mavenme.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class UserAuthInteractor {

    companion object {
        const val rcSignIn = 0x24
    }

    private val tag = UserAuthInteractor::class.java.simpleName
    private val auth = FirebaseAuth.getInstance()

    fun authUser(activity: AppCompatActivity): Boolean {
        if (auth.currentUser != null) return true
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.web_client_id))
                .requestEmail()
                .build()
        val client = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(client.signInIntent, rcSignIn)
        return false
    }

    fun googleAuthResult(activity: AppCompatActivity, data: Intent?, callBack: ((Boolean) -> Unit)) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(activity, account, callBack)
        } catch (e: ApiException) {
            Log.w(tag, "Google sign in failed", e)
            callBack.invoke(false)
        }
    }

    private fun firebaseAuthWithGoogle(activity: AppCompatActivity, account: GoogleSignInAccount?,
                                       callBack: ((Boolean) -> Unit)) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "signInWithCredential:success")
                        val user = auth.currentUser
                        MavenMeApplication.user = user
                        callBack.invoke(true)
                    } else {
                        Log.w(tag, "signInWithCredential:failure", task.exception)
                        callBack.invoke(false)
                    }
                }
    }

}