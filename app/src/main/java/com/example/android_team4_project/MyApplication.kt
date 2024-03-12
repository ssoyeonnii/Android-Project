package com.example.android_team4_project

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDexApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication:MultiDexApplication() {

    companion object {
        private lateinit var instance: MyApplication
        lateinit var auth: FirebaseAuth
        private var mGoogleSignInClient: GoogleSignInClient? = null

        private val signInCallback = MutableLiveData<GoogleSignInAccount?>()

        fun getSignInCallback(): MutableLiveData<GoogleSignInAccount?> {
            return signInCallback
        }


        fun getInstance(): MyApplication {
            return instance
        }

        private fun getGoogleSignInOptions(): GoogleSignInOptions {
            return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(instance.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        }

        private fun initializeGoogleSignInClient() {
            mGoogleSignInClient = GoogleSignIn.getClient(instance, getGoogleSignInOptions())
        }

        fun signInWithGoogle() {
            initializeGoogleSignInClient()
            val signInIntent = mGoogleSignInClient?.signInIntent
            instance.startActivity(signInIntent)
        }

        fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
            try {
                val account = completedTask.getResult(ApiException::class.java)
                signInCallback.value = account
            } catch (e: ApiException) {
                // Google Sign-In 실패 처리
                signInCallback.value = null
            }
        }
        var email: String? = null
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Firebase 인증 성공 시 처리
                    val intent = Intent(instance, MyActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    instance.startActivity(intent)
                } else {
                    // Firebase 인증 실패 시 처리
                    Toast.makeText(instance, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        instance = this
    }
    fun checkGoogleSignInAndNavigate() {
        val gsa = GoogleSignIn.getLastSignedInAccount(instance)

        if (gsa != null) {
            // Google 계정으로 로그인된 상태
            Toast.makeText(instance, R.string.status_login, Toast.LENGTH_SHORT).show()
            navigateToMyPage()
        } else {
            // Google 계정으로 로그인되지 않은 상태
            Toast.makeText(instance, R.string.status_not_login, Toast.LENGTH_SHORT).show()
            navigateToLoginPage()
        }
    }

    private fun navigateToMyPage() {
        val intent = Intent(this, MyActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}