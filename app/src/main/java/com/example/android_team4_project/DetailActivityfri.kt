package com.example.android_team4_project

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_team4_project.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
private lateinit var auth: FirebaseAuth

private lateinit var userEmail: String

class DetailActivityfri: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        userEmail = user?.uid ?: ""


        //      버튼클릭시 마이루틴으로 이동
        binding.btnMypage.setOnClickListener {
            // 이동할 액티비티코드
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 수정버튼 클릭시 해당요일 팝업엑티비티
        binding.btnSaveAll.setOnClickListener {
            // 이동할 액티비티코드
            val intent = Intent(this, PopupActivityfri::class.java)
            startActivity(intent)
        }

//        // 삭제버튼 클릭시 저장내용 삭제
//        binding.btnCancel.setOnClickListener {
//            showDeleteConfirmationDialog()
//        }
        binding.btnCancel.setOnClickListener {

            val view = View.inflate(this, R.layout.dialog_view, null)


            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            dialog.show()

            val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
            val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

            btnConfirm.setOnClickListener {
                showDeleteConfirmationDialog()
                Toast.makeText(this@DetailActivityfri, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))


        }


        // SharedPreferences에서 데이터 불러오기
        val sharedPrefTitlefri = getSharedPreferences("Routine_Fri$userEmail", Context.MODE_PRIVATE)

// 저장된 데이터 가져오기
        val savedTitlefri = sharedPrefTitlefri.getString("title", "")
        val savedSpinnerfri = sharedPrefTitlefri.getString("selectedSpinnerItem", "")
        val savedcon1fri = sharedPrefTitlefri.getString("con1", "")
        val savedcon2fri = sharedPrefTitlefri.getString("con2", "")
        val savedcon3fri = sharedPrefTitlefri.getString("con3", "")
        val savedcon4fri = sharedPrefTitlefri.getString("con4", "")
        val savedcon5fri = sharedPrefTitlefri.getString("con5", "")

        val savededm1fri = sharedPrefTitlefri.getString("edm1", "")
        val savededm2fri = sharedPrefTitlefri.getString("edm2", "")
        val savededm3fri = sharedPrefTitlefri.getString("edm3", "")
        val savededm4fri = sharedPrefTitlefri.getString("edm4", "")
        val savededm5fri = sharedPrefTitlefri.getString("edm5", "")

        val savededs1fri = sharedPrefTitlefri.getString("eds1", "")
        val savededs2fri = sharedPrefTitlefri.getString("eds2", "")
        val savededs3fri = sharedPrefTitlefri.getString("eds3", "")
        val savededs4fri = sharedPrefTitlefri.getString("eds4", "")
        val savededs5fri = sharedPrefTitlefri.getString("eds5", "")

// 가져온 데이터를 텍스트뷰에 설정
        binding.edTitle.text = savedTitlefri
        binding.spinner.text = savedSpinnerfri

        binding.edContent1.text = savedcon1fri
        binding.edContent2.text = savedcon2fri
        binding.edContent3.text = savedcon3fri
        binding.edContent4.text = savedcon4fri
        binding.edContent5.text = savedcon5fri

        binding.edm1.text = savededm1fri
        binding.edm2.text = savededm2fri
        binding.edm3.text = savededm3fri
        binding.edm4.text = savededm4fri
        binding.edm5.text = savededm5fri

        binding.eds1.text = savededs1fri
        binding.eds2.text = savededs2fri
        binding.eds3.text = savededs3fri
        binding.eds4.text = savededs4fri
        binding.eds5.text = savededs5fri
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("주의")
        builder.setMessage("삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { _, _ ->
            // 사용자가 삭제 버튼을 클릭했을 때
            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            userEmail = user?.uid ?: ""


            // RoutineData 객체 업데이트
            val routine = RoutineData(
                title = "",
                selectedSpinnerItem = "",
                con1 = "",
                con2 = "",
                con3 = "",
                con4 = "",
                con5 = "",
                edm1 = "",
                edm2 = "",
                edm3 = "",
                edm4 = "",
                edm5 = "",
                eds1 = "",
                eds2 = "",
                eds3 = "",
                eds4 = "",
                eds5 = ""
            )

            // RoutineData 객체를 사용하여 XML 데이터 생성
            val xmlData = generateXmlData(routine, "Fri")

            // userEmail을 이용하여 StorageManager를 초기화
            val storageManager = StorageManager(this)

            // StorageManager를 사용하여 XML 파일 업로드

            storageManager.uploadXmlFile(xmlData, "Fri")

            // SharedPreferences를 통해 데이터 저장
            val userUid = FirebaseAuth.getInstance().uid
            val sharedPrefTitlefri =
                getSharedPreferences("Routine_Fri$userUid", Context.MODE_PRIVATE)
            val editor = sharedPrefTitlefri.edit()

            editor.putString("title", routine.title)
            editor.putString("selectedSpinnerItem", routine.selectedSpinnerItem)
            editor.putString("con1", routine.con1)
            editor.putString("con2", routine.con2)
            editor.putString("con3", routine.con3)
            editor.putString("con4", routine.con4)
            editor.putString("con5", routine.con5)
            editor.putString("edm1", routine.edm1)
            editor.putString("edm2", routine.edm2)
            editor.putString("edm3", routine.edm3)
            editor.putString("edm4", routine.edm4)
            editor.putString("edm5", routine.edm5)
            editor.putString("eds1", routine.eds1)
            editor.putString("eds2", routine.eds2)
            editor.putString("eds3", routine.eds3)
            editor.putString("eds4", routine.eds4)
            editor.putString("eds5", routine.eds5)

            editor.apply()

            // 저장이 성공적으로 이루어지면 액티비티 이동
            val intent = Intent(this, MyActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            // 사용자가 취소 버튼을 클릭했을 때
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    private fun generateXmlData(routine: RoutineData, dayOfWeek:String): String {
        val xmlStringBuilder = StringBuilder()
        xmlStringBuilder.append("<routine>")
        xmlStringBuilder.append("<title>${routine.title}</title>")
        xmlStringBuilder.append("<con1>${routine.con1}</con1>")
        xmlStringBuilder.append("<con2>${routine.con2}</con2>")
        xmlStringBuilder.append("<con3>${routine.con3}</con3>")
        xmlStringBuilder.append("<con4>${routine.con4}</con4>")
        xmlStringBuilder.append("<con5>${routine.con5}</con5>")
        xmlStringBuilder.append("<edm1>${routine.edm1}</edm1>")
        xmlStringBuilder.append("<edm2>${routine.edm2}</edm2>")
        xmlStringBuilder.append("<edm3>${routine.edm3}</edm3>")
        xmlStringBuilder.append("<edm4>${routine.edm4}</edm4>")
        xmlStringBuilder.append("<edm5>${routine.edm5}</edm5>")
        xmlStringBuilder.append("<eds1>${routine.eds1}</eds1>")
        xmlStringBuilder.append("<eds2>${routine.eds2}</eds2>")
        xmlStringBuilder.append("<eds3>${routine.eds3}</eds3>")
        xmlStringBuilder.append("<eds4>${routine.eds4}</eds4>")
        xmlStringBuilder.append("<eds5>${routine.eds5}</eds5>")
        xmlStringBuilder.append("<selectedSpinnerItem>${routine.selectedSpinnerItem}</selectedSpinnerItem>")
        xmlStringBuilder.append("</routine>")

        return xmlStringBuilder.toString()
    }


}


