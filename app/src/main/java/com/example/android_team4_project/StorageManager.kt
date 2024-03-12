package com.example.android_team4_project

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StorageManager(context: Context) {

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val applicationContext = context.applicationContext
    private lateinit var mAuth: FirebaseAuth




    fun uploadXmlFile(xmlData: String,dayOfWeek:String) {
        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser
        val userEmail: String = user?.uid ?: ""



        val filePath = "Routine_${dayOfWeek}$userEmail.xml"

        val routineRef: StorageReference = storageRef.child(filePath)

        val data: ByteArray = xmlData.toByteArray()

        val uploadTask = routineRef.putBytes(data)

        uploadTask.addOnSuccessListener {
            // 업로드 성공 시
//            Toast.makeText(applicationContext, "XML 파일 업로드 성공", Toast.LENGTH_SHORT).show()

            // 여기에서 추가적인 작업을 수행할 수 있습니다.
        }.addOnFailureListener { exception ->
            // 업로드 실패 시
//            Toast.makeText(applicationContext, "XML 파일 업로드 실패", Toast.LENGTH_SHORT).show()

            // 에러 메시지를 출력하거나 추가적인 작업을 수행할 수 있습니다.
        }.addOnProgressListener { taskSnapshot ->
            // 업로드 진행 상황을 표시하거나 활용할 수 있습니다.
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            // 업로드 진행률을 사용하여 프로그레스 바 업데이트 등을 수행할 수 있습니다.
        }
    }


    fun downloadXmlFile(listener: DownloadFileListener, day: String) {

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser
        val userEmail: String = user?.uid ?: ""


        val filePath = "Routine_$day$userEmail.xml"

        val routineRef: StorageReference = storageRef.child(filePath)

        val ONE_MEGABYTE: Long = 1024 * 1024
        routineRef.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { data ->
                // 다운로드 성공 시
                val xmlData = String(data)
                listener.onDownloadSuccess(xmlData)

                // 여기에서 추가적인 작업을 수행할 수 있습니다.
            }.addOnFailureListener { exception ->
                // 다운로드 실패 시
                listener.onDownloadFailure()

                // 에러 메시지를 출력하거나 추가적인 작업을 수행할 수 있습니다.
                Log.d("StorageManager", "파일 다운로드에 실패했습니다: $filePath")
            }
    }

    interface DownloadFileListener {
        fun onDownloadSuccess(xmlData: String)
        fun onDownloadFailure()
    }
}