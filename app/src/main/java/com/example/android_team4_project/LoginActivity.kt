package com.example.android_team4_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android_team4_project.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding // View Binding 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // View Binding 초기화
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val signInButton = findViewById<SignInButton>(R.id.btnSignIn)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setColorScheme(SignInButton.COLOR_DARK)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnSignIn.setOnClickListener {
            signInWithGoogle()
        }
//        binding.btnSignOut.setOnClickListener {
//            signOut()
//        }
    }

    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)


    }

    // Google 로그인 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Firebase에 Google 로그인 정보 등록
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }

        } catch (e: ApiException) {
            Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user?.let { firebaseUser ->
                        val uid = user.uid // Firebase 사용자 UID 가져오기




                        // 다운로드 메서드 호출
                        downloadRoutineData(uid, object : StorageManager.DownloadFileListener {
                            override fun onDownloadSuccess(xmlData: String) {
                                // 다운로드 성공한 경우에 이후 처리를 수행
                                // xmlData를 파싱하여 필요한 내용을 가져와 사용할 수 있음
                                Log.d("KSC","다운로드 성공")

                                moveToActivity()
                            }

                            override fun onDownloadFailure() {
                                // 다운로드 실패한 경우에 처리
                                Log.d("KSC","다운로드 실패")

                                moveToActivity()
                            }
                        })
                    }
                } else {
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun downloadRoutineData(uid: String, listener: StorageManager.DownloadFileListener) {
        val storageManager = StorageManager(this)
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        var downloadCount = 0

        for (day in daysOfWeek) {
            val fileName = "Routine_$day$uid.xml"

            storageManager.downloadXmlFile(object : StorageManager.DownloadFileListener {
                override fun onDownloadSuccess(xmlData: String) {
                    val routineData = XmlParser().parseXml(xmlData)

                    // 파일에 루틴 데이터 저장
                    saveRoutineDataToShare(routineData, day, uid)

                    // 다운로드 완료 후 페이지 이동
                    downloadCount++
                    if (downloadCount == daysOfWeek.size) {
                        listener.onDownloadSuccess("")
                    }
                }

                override fun onDownloadFailure() {
                    // 다운로드 실패한 경우에 처리
                    Log.d("KSC","다운로드 실패")
                    downloadCount++
                    if (downloadCount == daysOfWeek.size) {
                        // 모든 데이터 다운로드가 완료된 경우에만 이동
                        listener.onDownloadFailure()
                    }
                }
            }, day) // day를 한 번만 전달하도록 수정
        }
    }

    private fun saveRoutineDataToShare(routineData: RoutineData, day: String, uid: String) {
        val sharedPrefTitle = getSharedPreferences("Routine_$day$uid", Context.MODE_PRIVATE)
        val editor = sharedPrefTitle.edit()

        editor.putString("title", routineData.title)
        editor.putString("selectedSpinnerItem", routineData.selectedSpinnerItem)
        editor.putString("con1", routineData.con1)
        editor.putString("con2", routineData.con2)
        editor.putString("con3", routineData.con3)
        editor.putString("con4", routineData.con4)
        editor.putString("con5", routineData.con5)
        editor.putString("edm1", routineData.edm1)
        editor.putString("edm2", routineData.edm2)
        editor.putString("edm3", routineData.edm3)
        editor.putString("edm4", routineData.edm4)
        editor.putString("edm5", routineData.edm5)
        editor.putString("eds1", routineData.eds1)
        editor.putString("eds2", routineData.eds2)
        editor.putString("eds3", routineData.eds3)
        editor.putString("eds4", routineData.eds4)
        editor.putString("eds5", routineData.eds5)

        editor.apply()

    }

//    private fun signOut() {
//        mAuth.signOut()
//        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
//            Toast.makeText(this, "Sign out successful", Toast.LENGTH_SHORT).show()
//        }
//    }


    // 해당부분을 이제 처음 시작했을 때  로그인 페이지에서 메인액티비티로 이동하도록 수정만 하면됨
    private fun moveToActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "GoogleSignIn"
    }
}
