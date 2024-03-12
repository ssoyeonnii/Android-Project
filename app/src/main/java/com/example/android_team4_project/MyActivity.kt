package com.example.android_team4_project

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.android_team4_project.databinding.ActivityLoginBinding

import com.example.android_team4_project.databinding.ActivityMyBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.io.InputStream


private val STORAGE_PERMISSION_CODE = 123

class MyActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        로그아웃을 위한 주소 불러오는 함수
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

//        로그아웃 리스너
        binding.btnSignOut.setOnClickListener {
            signOut()
        }

        val xmlData = intent.getStringExtra("xmlData")


        val Name = binding.Name

        FirebaseApp.initializeApp(this)

        val sharedPrefimg = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val dataToUpload = sharedPreferences.getString("userKey", "")


//        val db = FirebaseFirestore.getInstance()
//        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        val savedImageUriString = sharedPrefimg.getString("profileImageUri", "")
        val savedImageUri = Uri.parse(savedImageUriString)

        Glide.with(this)
            .load(savedImageUri)
            .into(binding.mainprofile)

//        갤러리 요청
        val reqGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == RESULT_OK) {
                        result.data?.data?.let { selectedImageUri ->
                            // 셈플 사이즈 비율계산 지정
                            val calRatio = calculateInSampleSize(
                                selectedImageUri,
                                resources.getDimensionPixelSize(R.dimen.imgSize),
                                resources.getDimensionPixelSize(R.dimen.imgSize)
                            )
                            val option = BitmapFactory.Options()
                            option.inSampleSize = calRatio

                            // 이미지 로딩
                            var inputStream: InputStream? =
                                contentResolver.openInputStream(selectedImageUri)
                            val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                            inputStream?.close()
                            inputStream = null
                            bitmap?.let {
                                binding.mainprofile.setImageBitmap(bitmap)
                                // 저장된 이미지 URI 업데이트
                                saveImageUriToSharedPreferences(selectedImageUri)
                            } ?: run {
                                Log.d("ksj", "bitmap null")
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        // 이미지뷰 클릭 이벤트 핸들링
        binding.mainprofile.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/*"
            reqGallery.launch(intent)
        }


//      버튼클릭시 마이루틴으로 이동
        binding.btnMypage.setOnClickListener {
            // 이동할 액티비티코드
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


// 수정 버튼 클릭 시
        binding.EditBtn.setOnClickListener {
            // 텍스트뷰와 수정버튼과 가로선을 숨기고 EditText와 체크버튼 표시
            binding.Name.visibility = View.GONE
            binding.Hr.visibility = View.GONE
            binding.EditBtn.visibility = View.GONE
            //체크버튼 비지블
            binding.EditName.visibility = View.VISIBLE
            binding.Checkbtn.visibility = View.VISIBLE

            // EditText에 텍스트뷰의 텍스트 설정
            binding.EditName.setText(binding.Name.text)

        }

// EditText에서 체크버튼 클릭시
        binding.Checkbtn.setOnClickListener {

            // EditText의 텍스트를 가져오기
            val editedName = binding.EditName.text.toString()

            // SharedPreferences를 통해 데이터 저장
            val sharedPref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("userName", editedName)
            editor.apply()

            //텍스트뷰에 수정된 이름 설정
            binding.Name.text = editedName


            // 텍스트뷰와 수정버튼과 가로선을 표시하고 EditText와 체크버튼 표시숨김
            binding.Name.visibility = View.VISIBLE
            binding.Hr.visibility = View.VISIBLE
            binding.EditBtn.visibility = View.VISIBLE
            //체크버튼 비지블
            binding.EditName.visibility = View.GONE
            binding.Checkbtn.visibility = View.GONE
        }

        // 앱을 시작할 때 SharedPreferences에서 데이터 불러오기
        val sharedPref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("userName", "사용자")

        // 텍스트뷰에 저장된 이름 설정
        binding.Name.text = savedName

        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnmon.setOnClickListener {
            val intent = Intent(this, PopupActivitymon::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtntue.setOnClickListener {
            val intent = Intent(this, PopupActivitytue::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnwed.setOnClickListener {
            val intent = Intent(this, PopupActivitywed::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnthu.setOnClickListener {
            val intent = Intent(this, PopupActivitythu::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnfri.setOnClickListener {
            val intent = Intent(this, PopupActivityfri::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnsat.setOnClickListener {
            val intent = Intent(this, PopupActivitysat::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //추가버튼 클릭시 팝업엑티비티로 이동
        binding.addBtnsun.setOnClickListener {
            val intent = Intent(this, PopupActivitysun::class.java)
            intent.putExtra("xmlData", xmlData)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnmon.setOnClickListener {
            val intent = Intent(this, PopupActivitymon::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtntue.setOnClickListener {
            val intent = Intent(this, PopupActivitytue::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnwed.setOnClickListener {
            val intent = Intent(this, PopupActivitywed::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnthu.setOnClickListener {
            val intent = Intent(this, PopupActivitythu::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnfri.setOnClickListener {
            val intent = Intent(this, PopupActivityfri::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnsat.setOnClickListener {
            val intent = Intent(this, PopupActivitysat::class.java)
            startActivity(intent)
        }
        //수정버튼 클릭시 팝업엑티비티로 이동
        binding.rewriteBtnsun.setOnClickListener {
            val intent = Intent(this, PopupActivitysun::class.java)
            startActivity(intent)
        }


        binding.mon.setOnClickListener {
            val intent = Intent(this, DetailActivitymon::class.java)
            startActivity(intent)
        }
        binding.tue.setOnClickListener {
            val intent = Intent(this, DetailActivitytue::class.java)
            startActivity(intent)
        }
        binding.wed.setOnClickListener {
            val intent = Intent(this, DetailActivitywed::class.java)
            startActivity(intent)
        }
        binding.thu.setOnClickListener {
            val intent = Intent(this, DetailActivitythu::class.java)
            startActivity(intent)
        }
        binding.fri.setOnClickListener {
            val intent = Intent(this, DetailActivityfri::class.java)
            startActivity(intent)
        }
        binding.sat.setOnClickListener {
            val intent = Intent(this, DetailActivitysat::class.java)
            startActivity(intent)
        }
        binding.sun.setOnClickListener {
            val intent = Intent(this, DetailActivitysun::class.java)
            startActivity(intent)
        }

        // 해당 부분 Uid값 받아와서 출력하는 것으로 수정

        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        val sharedPrefTitlemon = userUid?.let {
            getSharedPreferences("Routine_Mon$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_MonDefault", Context.MODE_PRIVATE)

        val sharedPrefTitletue = userUid?.let {
            getSharedPreferences("Routine_Tue$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_TueDefault", Context.MODE_PRIVATE)

        val sharedPrefTitlewed = userUid?.let {
            getSharedPreferences("Routine_Wed$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_WedDefault", Context.MODE_PRIVATE)

        val sharedPrefTitlethu = userUid?.let {
            getSharedPreferences("Routine_Thu$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_ThuDefault", Context.MODE_PRIVATE)

        val sharedPrefTitlefri = userUid?.let {
            getSharedPreferences("Routine_Fri$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_FriDefault", Context.MODE_PRIVATE)

        val sharedPrefTitlesat = userUid?.let {
            getSharedPreferences("Routine_Sat$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_SatDefault", Context.MODE_PRIVATE)

        val sharedPrefTitlesun = userUid?.let {
            getSharedPreferences("Routine_Sun$it", Context.MODE_PRIVATE)
        } ?: getSharedPreferences("Routine_SunDefault", Context.MODE_PRIVATE)

// 저장된 데이터 가져오기
        val savedTitlemon = sharedPrefTitlemon.getString("title", "")
        val savedSpinnermon = sharedPrefTitlemon.getString("selectedSpinnerItem", "")

        val savedTitletue = sharedPrefTitletue.getString("title", "")
        val savedSpinnertue = sharedPrefTitletue.getString("selectedSpinnerItem", "")

        val savedTitlewed = sharedPrefTitlewed.getString("title", "")
        val savedSpinnerwed = sharedPrefTitlewed.getString("selectedSpinnerItem", "")

        val savedTitlethu = sharedPrefTitlethu.getString("title", "")
        val savedSpinnerthu = sharedPrefTitlethu.getString("selectedSpinnerItem", "")

        val savedTitlefri = sharedPrefTitlefri.getString("title", "")
        val savedSpinnerfri = sharedPrefTitlefri.getString("selectedSpinnerItem", "")

        val savedTitlesat = sharedPrefTitlesat.getString("title", "")
        val savedSpinnersat = sharedPrefTitlesat.getString("selectedSpinnerItem", "")

        val savedTitlesun = sharedPrefTitlesun.getString("title", "")
        val savedSpinnersun = sharedPrefTitlesun.getString("selectedSpinnerItem", "")

// 가져온 데이터를 텍스트뷰에 설정
        binding.titlemon.text = savedTitlemon
        binding.spinnermon.text = savedSpinnermon

        binding.titletue.text = savedTitletue
        binding.spinnertue.text = savedSpinnertue

        binding.titlewed.text = savedTitlewed
        binding.spinnerwed.text = savedSpinnerwed

        binding.titlethu.text = savedTitlethu
        binding.spinnerthu.text = savedSpinnerthu

        binding.titlefri.text = savedTitlefri
        binding.spinnerfri.text = savedSpinnerfri

        binding.titlesat.text = savedTitlesat
        binding.spinnersat.text = savedSpinnersat

        binding.titlesun.text = savedTitlesun
        binding.spinnersun.text = savedSpinnersun

        if (!savedTitlemon.isNullOrBlank() && savedSpinnermon != "선택하기") {
            binding.addBtnmon.visibility = View.GONE
            binding.rewriteBtnmon.visibility = View.VISIBLE

            binding.rewriteBtnmon.setOnClickListener {
                val intent = Intent(this, PopupActivitymon::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnmon.visibility = View.VISIBLE
            binding.rewriteBtnmon.visibility = View.GONE
        }

        if (!savedTitletue.isNullOrBlank() && savedSpinnertue != "선택하기") {
            binding.addBtntue.visibility = View.GONE
            binding.rewriteBtntue.visibility = View.VISIBLE

            binding.rewriteBtntue.setOnClickListener {
                val intent = Intent(this, PopupActivitytue::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtntue.visibility = View.VISIBLE
            binding.rewriteBtntue.visibility = View.GONE
        }

        if (!savedTitlewed.isNullOrBlank() && savedSpinnerwed != "선택하기") {
            binding.addBtnwed.visibility = View.GONE
            binding.rewriteBtnwed.visibility = View.VISIBLE

            binding.rewriteBtnwed.setOnClickListener {
                val intent = Intent(this, PopupActivitywed::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnwed.visibility = View.VISIBLE
            binding.rewriteBtnwed.visibility = View.GONE
        }

        if (!savedTitlethu.isNullOrBlank() && savedSpinnerthu != "선택하기") {
            binding.addBtnthu.visibility = View.GONE
            binding.rewriteBtnthu.visibility = View.VISIBLE

            binding.rewriteBtnthu.setOnClickListener {
                val intent = Intent(this, PopupActivitythu::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnthu.visibility = View.VISIBLE
            binding.rewriteBtnthu.visibility = View.GONE
        }

        if (!savedTitlefri.isNullOrBlank() && savedSpinnerfri != "선택하기") {
            binding.addBtnfri.visibility = View.GONE
            binding.rewriteBtnfri.visibility = View.VISIBLE

            binding.rewriteBtnfri.setOnClickListener {
                val intent = Intent(this, PopupActivityfri::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnfri.visibility = View.VISIBLE
            binding.rewriteBtnfri.visibility = View.GONE
        }

        if (!savedTitlesat.isNullOrBlank() && savedSpinnersat != "선택하기") {
            binding.addBtnsat.visibility = View.GONE
            binding.rewriteBtnsat.visibility = View.VISIBLE

            binding.rewriteBtnsat.setOnClickListener {
                val intent = Intent(this, PopupActivitysat::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnsat.visibility = View.VISIBLE
            binding.rewriteBtnsat.visibility = View.GONE
        }

        if (!savedTitlesun.isNullOrBlank() && savedSpinnersun != "선택하기") {
            binding.addBtnsun.visibility = View.GONE
            binding.rewriteBtnsun.visibility = View.VISIBLE

            binding.rewriteBtnsun.setOnClickListener {
                val intent = Intent(this, PopupActivitysun::class.java)
                startActivity(intent)
            }
        } else {
            binding.addBtnsun.visibility = View.VISIBLE
            binding.rewriteBtnsun.visibility = View.GONE
        }


// "users" 컬렉션에 사용자 UID를 문서로 갖는 데이터 저장
//        db.collection("users").document(userUid!!)
//            .set(mapOf("data" to dataToUpload))
//            .addOnSuccessListener {
//                // 업로드 성공 시 처리
//            }
//            .addOnFailureListener {
//                // 업로드 실패 시 처리
//            }


        // FirebaseAuth에서 현재 사용자 정보 가져오기
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null && currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }) {
            // 사용자가 Google 계정으로 로그인한 경우
            // Google 계정에서 사용자 정보 가져오기
            val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

            // 사용자 닉네임을 TextView에 설정
            Name.text = googleSignInAccount?.displayName

        } else {
            // 사용자가 로그인되어 있지 않은 경우, 로그인 화면으로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 뒤로 가기 버튼으로 로그인 화면에 돌아가지 않도록 함
        }


    }


    //    로그아웃 실행부분 함수ㄴㅇ

    private fun signOut() {
        mAuth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            Toast.makeText(this, "Sign out successful", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this@MyActivity, LoginActivity::class.java)
        startActivity(intent)
    }


    //    이미지 저장
    private fun saveImageUriToSharedPreferences(imageUri: Uri) {
        val sharedPref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("profileImageUri", imageUri.toString())
        editor.apply()
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 경우, 권한을 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        } else {
            // 이미 권한이 부여된 경우에 대한 로직
        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}