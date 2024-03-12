package com.example.android_team4_project

import android.app.DatePickerDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.android_team4_project.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.DialogInterface
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    //타이머 성공실패 판단용 전역변수
    private var timesuccess1 = 0
    private var timesuccess2 = 0
    private var timesuccess3 = 0
    private var timesuccess4 = 0
    private var timesuccess5 = 0

    private lateinit var binding: ActivityMainBinding // View Binding 추가
//    private lateinit var intent: Intent

    //        하단 메뉴바로 frameLayout 동적 제어
    private lateinit var frameLayout1: FrameLayout
    private lateinit var frameLayout2: FrameLayout
    private lateinit var frameLayout3: FrameLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    private var savedPref1: String? = null
    private var savedPref2: String? = null
    private var savedPref3: String? = null
    private var savedPref4: String? = null
    private var savedPref5: String? = null

    private fun showFrameLayout(frameLayout: FrameLayout) {
        frameLayout1.visibility = if (frameLayout == frameLayout1) View.VISIBLE else View.INVISIBLE
        frameLayout2.visibility = if (frameLayout == frameLayout2) View.VISIBLE else View.INVISIBLE
        frameLayout3.visibility = if (frameLayout == frameLayout3) View.VISIBLE else View.INVISIBLE
    }


    // 전연함수 : 오늘날짜에 해당하는 요일가져오고 한글로 변환
    private fun getDayOfWeek(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // 요일을 한글로 변환
        return when (dayOfWeek) {
            Calendar.SUNDAY -> "일"
            Calendar.MONDAY -> "월"
            Calendar.TUESDAY -> "화"
            Calendar.WEDNESDAY -> "수"
            Calendar.THURSDAY -> "목"
            Calendar.FRIDAY -> "금"
            Calendar.SATURDAY -> "토"
            else -> ""
        }
    }

    private val nowDay: Int by lazy {
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    }

    //    요일별로 데이터 삽입
    private fun init(todayDayOfWeek: String) {
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        when (todayDayOfWeek) {
            "일" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlesun =
                    getSharedPreferences("Routine_Sun$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlesun = sharedPrefTitlesun.getString("title", "")
                val savedSpinnersun = sharedPrefTitlesun.getString("selectedSpinnerItem", "")
                val savedContentsun1 = sharedPrefTitlesun.getString("con1", "")
                val savedContentsun2 = sharedPrefTitlesun.getString("con2", "")
                val savedContentsun3 = sharedPrefTitlesun.getString("con3", "")
                val savedContentsun4 = sharedPrefTitlesun.getString("con4", "")
                val savedContentsun5 = sharedPrefTitlesun.getString("con5", "")
                val savededm1 = sharedPrefTitlesun.getString("edm1", "")
                val savededm2 = sharedPrefTitlesun.getString("edm2", "")
                val savededm3 = sharedPrefTitlesun.getString("edm3", "")
                val savededm4 = sharedPrefTitlesun.getString("edm4", "")
                val savededm5 = sharedPrefTitlesun.getString("edm5", "")
                val savededs1 = sharedPrefTitlesun.getString("eds1", "")
                val savededs2 = sharedPrefTitlesun.getString("eds2", "")
                val savededs3 = sharedPrefTitlesun.getString("eds3", "")
                val savededs4 = sharedPrefTitlesun.getString("eds4", "")
                val savededs5 = sharedPrefTitlesun.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlesun
                binding.getSpinner.text = savedSpinnersun
                binding.getRoutine1.text = savedContentsun1
                binding.getRoutine2.text = savedContentsun2
                binding.getRoutine3.text = savedContentsun3
                binding.getRoutine4.text = savedContentsun4
                binding.getRoutine5.text = savedContentsun5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnersun
                binding.getHomeTitle.text = savedTitlesun
            }

            "월" -> {
                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlemon =
                    getSharedPreferences("Routine_Mon$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlemon = sharedPrefTitlemon.getString("title", "")
                val savedSpinnermon = sharedPrefTitlemon.getString("selectedSpinnerItem", "")
                val savedContentmon1 = sharedPrefTitlemon.getString("con1", "")
                val savedContentmon2 = sharedPrefTitlemon.getString("con2", "")
                val savedContentmon3 = sharedPrefTitlemon.getString("con3", "")
                val savedContentmon4 = sharedPrefTitlemon.getString("con4", "")
                val savedContentmon5 = sharedPrefTitlemon.getString("con5", "")
                val savededm1 = sharedPrefTitlemon.getString("edm1", "")
                val savededm2 = sharedPrefTitlemon.getString("edm2", "")
                val savededm3 = sharedPrefTitlemon.getString("edm3", "")
                val savededm4 = sharedPrefTitlemon.getString("edm4", "")
                val savededm5 = sharedPrefTitlemon.getString("edm5", "")
                val savededs1 = sharedPrefTitlemon.getString("eds1", "")
                val savededs2 = sharedPrefTitlemon.getString("eds2", "")
                val savededs3 = sharedPrefTitlemon.getString("eds3", "")
                val savededs4 = sharedPrefTitlemon.getString("eds4", "")
                val savededs5 = sharedPrefTitlemon.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlemon
                binding.getSpinner.text = savedSpinnermon
                binding.getRoutine1.text = savedContentmon1
                binding.getRoutine2.text = savedContentmon2
                binding.getRoutine3.text = savedContentmon3
                binding.getRoutine4.text = savedContentmon4
                binding.getRoutine5.text = savedContentmon5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnermon
                binding.getHomeTitle.text = savedTitlemon
            }

            "화" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitletue =
                    getSharedPreferences("Routine_Tue$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitletue = sharedPrefTitletue.getString("title", "")
                val savedSpinnertue = sharedPrefTitletue.getString("selectedSpinnerItem", "")
                val savedContenttue1 = sharedPrefTitletue.getString("con1", "")
                val savedContenttue2 = sharedPrefTitletue.getString("con2", "")
                val savedContenttue3 = sharedPrefTitletue.getString("con3", "")
                val savedContenttue4 = sharedPrefTitletue.getString("con4", "")
                val savedContenttue5 = sharedPrefTitletue.getString("con5", "")
                val savededm1 = sharedPrefTitletue.getString("edm1", "")
                val savededm2 = sharedPrefTitletue.getString("edm2", "")
                val savededm3 = sharedPrefTitletue.getString("edm3", "")
                val savededm4 = sharedPrefTitletue.getString("edm4", "")
                val savededm5 = sharedPrefTitletue.getString("edm5", "")
                val savededs1 = sharedPrefTitletue.getString("eds1", "")
                val savededs2 = sharedPrefTitletue.getString("eds2", "")
                val savededs3 = sharedPrefTitletue.getString("eds3", "")
                val savededs4 = sharedPrefTitletue.getString("eds4", "")
                val savededs5 = sharedPrefTitletue.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitletue
                binding.getSpinner.text = savedSpinnertue
                binding.getRoutine1.text = savedContenttue1
                binding.getRoutine2.text = savedContenttue2
                binding.getRoutine3.text = savedContenttue3
                binding.getRoutine4.text = savedContenttue4
                binding.getRoutine5.text = savedContenttue5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnertue
                binding.getHomeTitle.text = savedTitletue

            }

            "수" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlewed =
                    getSharedPreferences("Routine_Wed$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlewed = sharedPrefTitlewed.getString("title", "")
                val savedSpinnerwed = sharedPrefTitlewed.getString("selectedSpinnerItem", "")
                val savedContentwed1 = sharedPrefTitlewed.getString("con1", "")
                val savedContentwed2 = sharedPrefTitlewed.getString("con2", "")
                val savedContentwed3 = sharedPrefTitlewed.getString("con3", "")
                val savedContentwed4 = sharedPrefTitlewed.getString("con4", "")
                val savedContentwed5 = sharedPrefTitlewed.getString("con5", "")
                val savededm1 = sharedPrefTitlewed.getString("edm1", "")
                val savededm2 = sharedPrefTitlewed.getString("edm2", "")
                val savededm3 = sharedPrefTitlewed.getString("edm3", "")
                val savededm4 = sharedPrefTitlewed.getString("edm4", "")
                val savededm5 = sharedPrefTitlewed.getString("edm5", "")
                val savededs1 = sharedPrefTitlewed.getString("eds1", "")
                val savededs2 = sharedPrefTitlewed.getString("eds2", "")
                val savededs3 = sharedPrefTitlewed.getString("eds3", "")
                val savededs4 = sharedPrefTitlewed.getString("eds4", "")
                val savededs5 = sharedPrefTitlewed.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlewed
                binding.getSpinner.text = savedSpinnerwed
                binding.getRoutine1.text = savedContentwed1
                binding.getRoutine2.text = savedContentwed2
                binding.getRoutine3.text = savedContentwed3
                binding.getRoutine4.text = savedContentwed4
                binding.getRoutine5.text = savedContentwed5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnerwed
                binding.getHomeTitle.text = savedTitlewed

            }

            "목" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlethu =
                    getSharedPreferences("Routine_Thu$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlethu = sharedPrefTitlethu.getString("title", "")
                val savedSpinnerthu = sharedPrefTitlethu.getString("selectedSpinnerItem", "")
                val savedContentthu1 = sharedPrefTitlethu.getString("con1", "")
                val savedContentthu2 = sharedPrefTitlethu.getString("con2", "")
                val savedContentthu3 = sharedPrefTitlethu.getString("con3", "")
                val savedContentthu4 = sharedPrefTitlethu.getString("con4", "")
                val savedContentthu5 = sharedPrefTitlethu.getString("con5", "")
                val savededm1 = sharedPrefTitlethu.getString("edm1", "")
                val savededm2 = sharedPrefTitlethu.getString("edm2", "")
                val savededm3 = sharedPrefTitlethu.getString("edm3", "")
                val savededm4 = sharedPrefTitlethu.getString("edm4", "")
                val savededm5 = sharedPrefTitlethu.getString("edm5", "")
                val savededs1 = sharedPrefTitlethu.getString("eds1", "")
                val savededs2 = sharedPrefTitlethu.getString("eds2", "")
                val savededs3 = sharedPrefTitlethu.getString("eds3", "")
                val savededs4 = sharedPrefTitlethu.getString("eds4", "")
                val savededs5 = sharedPrefTitlethu.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlethu
                binding.getSpinner.text = savedSpinnerthu
                binding.getRoutine1.text = savedContentthu1
                binding.getRoutine2.text = savedContentthu2
                binding.getRoutine3.text = savedContentthu3
                binding.getRoutine4.text = savedContentthu4
                binding.getRoutine5.text = savedContentthu5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnerthu
                binding.getHomeTitle.text = savedTitlethu

            }

            "금" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlefri =
                    getSharedPreferences("Routine_Fri$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlefri = sharedPrefTitlefri.getString("title", "")
                val savedSpinnerfri = sharedPrefTitlefri.getString("selectedSpinnerItem", "")
                val savedContentfri1 = sharedPrefTitlefri.getString("con1", "")
                val savedContentfri2 = sharedPrefTitlefri.getString("con2", "")
                val savedContentfri3 = sharedPrefTitlefri.getString("con3", "")
                val savedContentfri4 = sharedPrefTitlefri.getString("con4", "")
                val savedContentfri5 = sharedPrefTitlefri.getString("con5", "")
                val savededm1 = sharedPrefTitlefri.getString("edm1", "")
                val savededm2 = sharedPrefTitlefri.getString("edm2", "")
                val savededm3 = sharedPrefTitlefri.getString("edm3", "")
                val savededm4 = sharedPrefTitlefri.getString("edm4", "")
                val savededm5 = sharedPrefTitlefri.getString("edm5", "")
                val savededs1 = sharedPrefTitlefri.getString("eds1", "")
                val savededs2 = sharedPrefTitlefri.getString("eds2", "")
                val savededs3 = sharedPrefTitlefri.getString("eds3", "")
                val savededs4 = sharedPrefTitlefri.getString("eds4", "")
                val savededs5 = sharedPrefTitlefri.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlefri
                binding.getSpinner.text = savedSpinnerfri
                binding.getRoutine1.text = savedContentfri1
                binding.getRoutine2.text = savedContentfri2
                binding.getRoutine3.text = savedContentfri3
                binding.getRoutine4.text = savedContentfri4
                binding.getRoutine5.text = savedContentfri5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnerfri
                binding.getHomeTitle.text = savedTitlefri

            }

            "토" -> {

                //        sharedPref에서 데이터 불러오기
                val sharedPrefTitlesat =
                    getSharedPreferences("Routine_Sat$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                val savedTitlesat = sharedPrefTitlesat.getString("title", "")
                val savedSpinnersat = sharedPrefTitlesat.getString("selectedSpinnerItem", "")
                val savedContentsat1 = sharedPrefTitlesat.getString("con1", "")
                val savedContentsat2 = sharedPrefTitlesat.getString("con2", "")
                val savedContentsat3 = sharedPrefTitlesat.getString("con3", "")
                val savedContentsat4 = sharedPrefTitlesat.getString("con4", "")
                val savedContentsat5 = sharedPrefTitlesat.getString("con5", "")
                val savededm1 = sharedPrefTitlesat.getString("edm1", "")
                val savededm2 = sharedPrefTitlesat.getString("edm2", "")
                val savededm3 = sharedPrefTitlesat.getString("edm3", "")
                val savededm4 = sharedPrefTitlesat.getString("edm4", "")
                val savededm5 = sharedPrefTitlesat.getString("edm5", "")
                val savededs1 = sharedPrefTitlesat.getString("eds1", "")
                val savededs2 = sharedPrefTitlesat.getString("eds2", "")
                val savededs3 = sharedPrefTitlesat.getString("eds3", "")
                val savededs4 = sharedPrefTitlesat.getString("eds4", "")
                val savededs5 = sharedPrefTitlesat.getString("eds5", "")


//        null값 허용
                val intSavededm1 = savededm1?.toIntOrNull() ?: 0
                val intSavededs1 = savededs1?.toIntOrNull() ?: 0
//        00분 00초 형식으로 변환
                val formattedSavededm1 = "%02d".format(intSavededm1)
                val formattedSavededs1 = "%02d".format(intSavededs1)
                val timeText1 = "${formattedSavededm1}분 ${formattedSavededs1}초"

                val intSavededm2 = savededm2?.toIntOrNull() ?: 0
                val intSavededs2 = savededs2?.toIntOrNull() ?: 0
                val formattedSavededm2 = "%02d".format(intSavededm2)
                val formattedSavededs2 = "%02d".format(intSavededs2)
                val timeText2 = "${formattedSavededm2}분 ${formattedSavededs2}초"

                val intSavededm3 = savededm3?.toIntOrNull() ?: 0
                val intSavededs3 = savededs3?.toIntOrNull() ?: 0
                val formattedSavededm3 = "%02d".format(intSavededm3)
                val formattedSavededs3 = "%02d".format(intSavededs3)
                val timeText3 = "${formattedSavededm3}분 ${formattedSavededs3}초"

                val intSavededm4 = savededm4?.toIntOrNull() ?: 0
                val intSavededs4 = savededs4?.toIntOrNull() ?: 0
                val formattedSavededm4 = "%02d".format(intSavededm4)
                val formattedSavededs4 = "%02d".format(intSavededs4)
                val timeText4 = "${formattedSavededm4}분 ${formattedSavededs4}초"

                val intSavededm5 = savededm5?.toIntOrNull() ?: 0
                val intSavededs5 = savededs5?.toIntOrNull() ?: 0
                val formattedSavededm5 = "%02d".format(intSavededm5)
                val formattedSavededs5 = "%02d".format(intSavededs5)
                val timeText5 = "${formattedSavededm5}분 ${formattedSavededs5}초"


//        가져온 데이터를 텍스트뷰에 설정
                binding.getTitle.text = savedTitlesat
                binding.getSpinner.text = savedSpinnersat
                binding.getRoutine1.text = savedContentsat1
                binding.getRoutine2.text = savedContentsat2
                binding.getRoutine3.text = savedContentsat3
                binding.getRoutine4.text = savedContentsat4
                binding.getRoutine5.text = savedContentsat5
                binding.getTime1.text = timeText1
                binding.getTime2.text = timeText2
                binding.getTime3.text = timeText3
                binding.getTime4.text = timeText4
                binding.getTime5.text = timeText5
                binding.getHomeSpinner.text = savedSpinnersat
                binding.getHomeTitle.text = savedTitlesat

            }

            else -> {
//                 그 외의 경우
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // View Binding 초기화
        setContentView(binding.root)

        //        루틴 스탑워치 저장 후 루틴성공 알람
        cancelNotification()

        //        하단탭바 작동시키기
        frameLayout1 = findViewById(R.id.Home1)
        frameLayout2 = findViewById(R.id.Home2)
        frameLayout3 = findViewById(R.id.Home3)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // 초기 탭 설정
        showFrameLayout(frameLayout1)

        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home1 -> showFrameLayout(frameLayout1)
                R.id.Home2 -> showFrameLayout(frameLayout2)
                R.id.Home3 -> showFrameLayout(frameLayout3)
            }
            true
        }

        // 현재 날짜의 요일을 가져오기
        val todayDayOfWeek = getDayOfWeek()

        // 현재 요일에 따라 분기
        init(todayDayOfWeek)

//        달력생성 및 날짜를 두번째, 세번째 페이지에 출력
        val dayText2: TextView = findViewById(R.id.day_text2)
        val dayText3: TextView = findViewById(R.id.day_text3)
        val calendarView: CalendarView = findViewById(R.id.calendarView)

//        닐짜 형태
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

//        오늘 날짜
        val date: Date = Date(calendarView.date)


//        날짜 텍스트뷰에 담기
        dayText2.text = dateFormat.format(date) // 두번째 페이지는 오늘 날짜만 받아옴
        dayText3.text = dateFormat.format(date)

//        캘린더 날짜 변환 이벤트
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // 요일을 얻기 위해 Calendar 클래스의 get 메서드와 Calendar.DAY_OF_WEEK 상수 사용
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            val sharedPref = getSharedPreferences("Write_$currentDate", Context.MODE_PRIVATE)
            val memo = sharedPref.getString("memo", "")
            binding.getHomeMemo.text = memo

            // dayOfWeek 값에 따라 요일별 동작 정의
            when (dayOfWeek) {
                Calendar.SUNDAY -> {

                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlesun =
                        getSharedPreferences("Routine_Sun$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlesun = sharedPrefTitlesun.getString("title", "")
                    val savedSpinnersun = sharedPrefTitlesun.getString("selectedSpinnerItem", "")

//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnersun
                    binding.getHomeTitle.text = savedTitlesun
                }

                Calendar.MONDAY -> {
                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlemon =
                        getSharedPreferences("Routine_Mon$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlemon = sharedPrefTitlemon.getString("title", "")
                    val savedSpinnermon = sharedPrefTitlemon.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnermon
                    binding.getHomeTitle.text = savedTitlemon
                }

                Calendar.TUESDAY -> {
                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitletue =
                        getSharedPreferences("Routine_Tue$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitletue = sharedPrefTitletue.getString("title", "")
                    val savedSpinnertue = sharedPrefTitletue.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnertue
                    binding.getHomeTitle.text = savedTitletue
                }

                Calendar.WEDNESDAY -> {
                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlewed =
                        getSharedPreferences("Routine_Wen$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlewed = sharedPrefTitlewed.getString("title", "")
                    val savedSpinnerwed = sharedPrefTitlewed.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnerwed
                    binding.getHomeTitle.text = savedTitlewed
                }

                Calendar.THURSDAY -> {
                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlethu =
                        getSharedPreferences("Routine_Thu$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlethu = sharedPrefTitlethu.getString("title", "")
                    val savedSpinnerthu = sharedPrefTitlethu.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnerthu
                    binding.getHomeTitle.text = savedTitlethu
                }

                Calendar.FRIDAY -> {

                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlefri =
                        getSharedPreferences("Routine_Fri$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlefri = sharedPrefTitlefri.getString("title", "")
                    val savedSpinnerfri = sharedPrefTitlefri.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnerfri
                    binding.getHomeTitle.text = savedTitlefri
                }

                Calendar.SATURDAY -> {
                    //        sharedPref에서 데이터 불러오기
                    val sharedPrefTitlesat =
                        getSharedPreferences("Routine_Sat$userUid", Context.MODE_PRIVATE)

//        저장된 데이터 가져오기
                    val savedTitlesat = sharedPrefTitlesat.getString("title", "")
                    val savedSpinnersat = sharedPrefTitlesat.getString("selectedSpinnerItem", "")


//        가져온 데이터를 텍스트뷰에 설정
                    // home페이지
                    binding.getHomeSpinner.text = savedSpinnersat
                    binding.getHomeTitle.text = savedTitlesat
                }

                else -> {
                    "일정이 없습니다"
                }
            }

//            날짜 변수에 담기
            var day: String = "${year}년 ${month + 1}월 ${dayOfMonth}일"


//            변수를 textView에 담기
//            dayText3.text = day
        }

//        상단 프로필 이동 리스너
        binding.profile1.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            val user = mAuth.currentUser
            if (user != null) {
                // 사용자가 로그인한 경우
                val intent = Intent(this, MyActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        binding.profile2.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            val user = mAuth.currentUser
            if (user != null) {
                // 사용자가 로그인한 경우
                val intent = Intent(this, MyActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        binding.profile3.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            val user = mAuth.currentUser
            if (user != null) {
                // 사용자가 로그인한 경우
                val intent = Intent(this, MyActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }


//        첫번째 페이지


//        두번째 화면
        //1. 앱실행시 리스트페이지에 오늘날짜 기준 데이터 뿌려주기
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val sharedPref = getSharedPreferences("Write_$currentDate", Context.MODE_PRIVATE)

        // 파일에서 데이터 읽기
        val title = sharedPref.getString("title", "")
        val spinner = sharedPref.getString("spinner", "")

        val routin1 = sharedPref.getString("con1", "")
        val routin2 = sharedPref.getString("con2", "")
        val routin3 = sharedPref.getString("con3", "")
        val routin4 = sharedPref.getString("con4", "")
        val routin5 = sharedPref.getString("con5", "")

        val time1 = sharedPref.getString("time1", "")
        val time2 = sharedPref.getString("time2", "")
        val time3 = sharedPref.getString("time3", "")
        val time4 = sharedPref.getString("time4", "")
        val time5 = sharedPref.getString("time5", "")
        val tvTimerTotal = sharedPref.getString("tvTimerTotal", "")

        val memo = sharedPref.getString("memo", "")

        val realTime1 = sharedPref.getString("realTime1", "")
        val realTime2 = sharedPref.getString("realTime2", "")
        val realTime3 = sharedPref.getString("realTime3", "")
        val realTime4 = sharedPref.getString("realTime4", "")
        val realTime5 = sharedPref.getString("realTime5", "")

        var timesuccess11 = sharedPref.getInt("timedetect1", 0)
        var timesuccess21 = sharedPref.getInt("timedetect2", 0)
        var timesuccess31 = sharedPref.getInt("timedetect3", 0)
        var timesuccess41 = sharedPref.getInt("timedetect4", 0)
        var timesuccess51 = sharedPref.getInt("timedetect5", 0)

        // UI에 데이터 연동
        binding.showTitle.text = title
        binding.showSpinner.text = spinner
        binding.showRoutine1.text = routin1
        binding.showRoutine2.text = routin2
        binding.showRoutine3.text = routin3
        binding.showRoutine4.text = routin4
        binding.showRoutine5.text = routin5
        binding.showTime1.text = time1
        binding.showTime2.text = time2
        binding.showTime3.text = time3
        binding.showTime4.text = time4
        binding.showTime5.text = time5
        binding.tvShowTimerTotal.text = tvTimerTotal
        binding.showMemo.text = memo
        binding.showRealTime1.text = realTime1
        binding.showRealTime2.text = realTime2
        binding.showRealTime3.text = realTime3
        binding.showRealTime4.text = realTime4
        binding.showRealTime5.text = realTime5
        binding.getHomeMemo.text = memo

        if (timesuccess11 == 1) {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess11 == 2) {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess21 == 1) {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess21 == 2) {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess31 == 1) {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess31 == 2) {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess41 == 1) {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess41 == 2) {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess51 == 1) {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess51 == 2) {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        // 2. 저장하기 버튼 클릭시 불러온 텍스트뷰값 그대로 저장(단, 일자별로 저장)
        binding.btnSaveAll.setOnClickListener {
            //Toast
            Toast.makeText(this,"데이터가 저장되었습니다.",Toast.LENGTH_SHORT).show()
            // 텍스트값 가져오기
            val Spinner = binding.getSpinner.text.toString()
            val Title = binding.getTitle.text.toString()
            val routin1 = binding.getRoutine1.text.toString()
            val routin2 = binding.getRoutine2.text.toString()
            val routin3 = binding.getRoutine3.text.toString()
            val routin4 = binding.getRoutine4.text.toString()
            val routin5 = binding.getRoutine5.text.toString()

            val time1 = binding.getTime1.text.toString()
            val time2 = binding.getTime2.text.toString()
            val time3 = binding.getTime3.text.toString()
            val time4 = binding.getTime4.text.toString()
            val time5 = binding.getTime5.text.toString()
            val tvTimerTotal = binding.tvTimerTotal.text.toString()

            val memo = binding.memo.text.toString()

            val realTime1 = binding.tvTimer.text.toString()
            val realTime2 = binding.tvTimer2.text.toString()
            val realTime3 = binding.tvTimer3.text.toString()
            val realTime4 = binding.tvTimer4.text.toString()
            val realTime5 = binding.tvTimer5.text.toString()


            // SharedPreferences를 통해 데이터 날짜별로 저장
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val sharedPref = getSharedPreferences("Write_$currentDate", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            // 파일에 저장
            editor.putString("title", Title)
            editor.putString("spinner", Spinner)

            editor.putString("con1", routin1)
            editor.putString("con2", routin2)
            editor.putString("con3", routin3)
            editor.putString("con4", routin4)
            editor.putString("con5", routin5)

            editor.putString("time1", time1)
            editor.putString("time2", time2)
            editor.putString("time3", time3)
            editor.putString("time4", time4)
            editor.putString("time5", time5)
            editor.putString("tvTimerTotal", tvTimerTotal)

            editor.putString("memo", memo)

            editor.putInt("timedetect1", timesuccess1)
            editor.putInt("timedetect2", timesuccess2)
            editor.putInt("timedetect3", timesuccess3)
            editor.putInt("timedetect4", timesuccess4)
            editor.putInt("timedetect5", timesuccess5)

            editor.putString("realTime1", realTime1)
            editor.putString("realTime2", realTime2)
            editor.putString("realTime3", realTime3)
            editor.putString("realTime4", realTime4)
            editor.putString("realTime5", realTime5)

            editor.apply()

            //저장 성공후 동작정의


            // 파일에서 데이터 읽기
            val title1 = sharedPref.getString("title", "")
            val spinner1 = sharedPref.getString("spinner", "")

            val routin11 = sharedPref.getString("con1", "")
            val routin21 = sharedPref.getString("con2", "")
            val routin31 = sharedPref.getString("con3", "")
            val routin41 = sharedPref.getString("con4", "")
            val routin51 = sharedPref.getString("con5", "")

            val time11 = sharedPref.getString("time1", "")
            val time21 = sharedPref.getString("time2", "")
            val time31 = sharedPref.getString("time3", "")
            val time41 = sharedPref.getString("time4", "")
            val time51 = sharedPref.getString("time5", "")
            val tvTimerTotal1 = sharedPref.getString("tvTimerTotal", "")

            val memo1 = sharedPref.getString("memo", "")

            var timesuccess11 = sharedPref.getInt("timedetect1", 0)
            var timesuccess21 = sharedPref.getInt("timedetect2", 0)
            var timesuccess31 = sharedPref.getInt("timedetect3", 0)
            var timesuccess41 = sharedPref.getInt("timedetect4", 0)
            var timesuccess51 = sharedPref.getInt("timedetect5", 0)

            val realTime11 = sharedPref.getString("realTime1", "")
            val realTime21 = sharedPref.getString("realTime2", "")
            val realTime31 = sharedPref.getString("realTime3", "")
            val realTime41 = sharedPref.getString("realTime4", "")
            val realTime51 = sharedPref.getString("realTime5", "")


            // UI에 데이터 연동
            binding.showTitle.text = title1
            binding.showSpinner.text = spinner1
            binding.showRoutine1.text = routin11
            binding.showRoutine2.text = routin21
            binding.showRoutine3.text = routin31
            binding.showRoutine4.text = routin41
            binding.showRoutine5.text = routin51
            binding.showTime1.text = time11
            binding.showTime2.text = time21
            binding.showTime3.text = time31
            binding.showTime4.text = time41
            binding.showTime5.text = time51
            binding.tvShowTimerTotal.text = tvTimerTotal1
            binding.showMemo.text = memo1
            binding.getHomeMemo.text = memo1
            val editableMemo = Editable.Factory.getInstance().newEditable(memo1)
            binding.memo.text = editableMemo
            binding.showRealTime1.text = realTime11
            binding.showRealTime2.text = realTime21
            binding.showRealTime3.text = realTime31
            binding.showRealTime4.text = realTime41
            binding.showRealTime5.text = realTime51


            if (timesuccess11 == 1) {
                binding.form1.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            } else if (timesuccess11 == 2) {
                binding.form1.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.form1.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }

            if (timesuccess21 == 1) {
                binding.form2.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            } else if (timesuccess21 == 2) {
                binding.form2.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.form2.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }

            if (timesuccess31 == 1) {
                binding.form3.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            } else if (timesuccess31 == 2) {
                binding.form3.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.form3.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }

            if (timesuccess41 == 1) {
                binding.form4.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            } else if (timesuccess41 == 2) {
                binding.form4.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.form4.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }

            if (timesuccess51 == 1) {
                binding.form5.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            } else if (timesuccess51 == 2) {
                binding.form5.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.form5.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }


        }





// 첫번째 루틴 타이머
// 타이머화면으로 이동
        binding.btnPlay1.setOnClickListener {
            intent = Intent(this, StopActivity1::class.java)
            intent.putExtra("isGetTime1", binding.getTime1.text.toString())
            startActivity(intent)
        }
        // 타이머 값 받아오기
//        val value1 = intent.getStringExtra("times1")

        val sharedPref1 = getSharedPreferences("stop1", Context.MODE_PRIVATE)
        savedPref1 = sharedPref1.getString("times1", "")

        val routinePackage1 = findViewById<RelativeLayout>(R.id.routinePackage1)
        val getTime1 = findViewById<TextView>(R.id.getTime1)

        // 사용
        if (savedPref1.isNullOrEmpty() || savedPref1!!.length < 5) {
            // 초기화되지 않았거나 유효한 값이 없는 경우
            routinePackage1.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val laterText1 = getTime1.text.toString()

            if (savedPref1!!.startsWith(laterText1.substring(0, 5))) {
                routinePackage1.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                routinePackage1.setBackgroundColor(Color.TRANSPARENT)
            }

            val savedValue11 = savedPref1.toString()
            binding.tvTimer.text =
                "${savedValue11.substring(0, 2)}분 ${savedValue11.substring(3, 5)}초"

            if (getTime1.text.toString().compareTo(binding.tvTimer.text.toString()) <= 0) {
                timesuccess1 = 1
                routinePackage1.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                timesuccess1 = 2
                routinePackage1.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }

//            값 받아오면 remove버튼 소환
            binding.btnPlay1.visibility = View.GONE
            binding.btnRemove1.visibility = View.VISIBLE

            showFrameLayout(frameLayout2)

        }

        //        remove1버튼 동작 - 색깔 , 저장된 스탑워치 값 리셋
        binding.btnRemove1.setOnClickListener {

            binding.tvTimer.setText("")

            timesuccess1 = 0

            // SharedPreferences에서 Editor 생성
            val editor = sharedPref1.edit()

            // times1 키에 해당하는 데이터 제거
            editor.remove("times1")

            // 변경사항을 적용
            editor.commit()

            // UI 업데이트 등 필요한 작업 수행
            routinePackage1.setBackgroundColor(Color.TRANSPARENT)

            binding.btnPlay1.visibility = View.VISIBLE
            binding.btnRemove1.visibility = View.GONE

            //times1삭제되었으나 savedPref1은 해당 값이 삭제되기 전의 값을 그대로 유지함 그래서 초기화해주기
            savedPref1 = null

            // calculateAndSetTotalTime 함수 호출
            val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
            calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)



        }


// 두번째 루틴 타이머
        binding.btnPlay2.setOnClickListener {
            intent = Intent(this, StopActivity2::class.java)
            intent.putExtra("isGetTime2", binding.getTime2.text.toString())
            startActivity(intent)

        }


//        val value2 = intent.getStringExtra("times2")

        val sharedPref2 = getSharedPreferences("stop2", Context.MODE_PRIVATE)
        savedPref2 = sharedPref2.getString("times2", "")

        val routinePackage2 = findViewById<RelativeLayout>(R.id.routinePackage2)
        val getTime2 = findViewById<TextView>(R.id.getTime2)

// 사용
        if (savedPref2.isNullOrEmpty() || savedPref2!!.length < 5) {
            // 초기화되지 않았거나 유효한 값이 없는 경우
            routinePackage2.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val laterText2 = getTime2.text.toString()

            if (savedPref2!!.startsWith(laterText2.substring(0, 5))) {
                routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                routinePackage2.setBackgroundColor(Color.TRANSPARENT)
            }

            val savedValue22 = savedPref2.toString()
            binding.tvTimer2.text =
                "${savedValue22.substring(0, 2)}분 ${savedValue22.substring(3, 5)}초"

            if (getTime2.text.toString().compareTo(binding.tvTimer2.text.toString()) <= 0) {
                timesuccess2 = 1
                routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                timesuccess2 = 2
                routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }

//            값 받아오면 remove버튼 소환
            binding.btnPlay2.visibility = View.GONE
            binding.btnRemove2.visibility = View.VISIBLE

            showFrameLayout(frameLayout2)
        }

//        remove2버튼 동작 - 색깔 , 저장된 스탑워치 값 리셋
        binding.btnRemove2.setOnClickListener {

            binding.tvTimer2.setText("")


            timesuccess2 = 0

            // SharedPreferences에서 Editor 생성
            val editor = sharedPref2.edit()

            // times2 키에 해당하는 데이터 제거
            editor.remove("times2")

            // 변경사항을 적용
            editor.commit()

            // UI 업데이트 등 필요한 작업 수행
            routinePackage2.setBackgroundColor(Color.TRANSPARENT)

            binding.btnPlay2.visibility = View.VISIBLE
            binding.btnRemove2.visibility = View.GONE

            savedPref2 = null

            // calculateAndSetTotalTime 함수 호출
            val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
            calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)


        }
//        binding.btnPlay2.setOnClickListener {
//            intent = Intent(this, StopActivity2::class.java)
//            startActivity(intent)
//        }
//        val value2 = intent.getStringExtra("times2")
//
//        val routinePackage2 = findViewById<RelativeLayout>(R.id.routinePackage2)
//
//        val getTime2 = findViewById<TextView>(R.id.getTime2)
//
//        val laterText2 = getTime2.text.toString()
//
//        if (value2 != null && value2.startsWith(laterText2.substring(0, 5))) {
//            routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//
//        } else {
//            routinePackage2.setBackgroundColor(Color.TRANSPARENT) // 초록색이 아닌 경우 투명 배경으로 초기화
//        }
//
//
//        if (value2 == null) {
//            binding.tvTimer2.text = "소요시간"
//        } else {
//            val value22 = value2.toString()
//            binding.tvTimer2.text = value22.substring(0, 2) + "분 " + value22.substring(3, 5) + "초"
//
//            if (getTime2.text.toString().compareTo(binding.tvTimer2.text.toString()) <= 0) {
//                routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//            } else {
//                routinePackage2.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
//            }
//        }

// 세번째 루틴 타이머
        binding.btnPlay3.setOnClickListener {
            intent = Intent(this, StopActivity3::class.java)
            intent.putExtra("isGetTime3", binding.getTime3.text.toString())
            startActivity(intent)
        }
//        val value3 = intent.getStringExtra("times3")

// 초기화
        val sharedPref3 = getSharedPreferences("stop3", Context.MODE_PRIVATE)
        savedPref3 = sharedPref3.getString("times3", "")

        val routinePackage3 = findViewById<RelativeLayout>(R.id.routinePackage3)
        val getTime3 = findViewById<TextView>(R.id.getTime3)

// 사용
        if (savedPref3.isNullOrEmpty() || savedPref3!!.length < 5) {
            // 초기화되지 않았거나 유효한 값이 없는 경우
            routinePackage3.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val laterText3 = getTime3.text.toString()

            if (savedPref3!!.startsWith(laterText3.substring(0, 5))) {
                routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                routinePackage3.setBackgroundColor(Color.TRANSPARENT)
            }

            val savedValue33 = savedPref3.toString()
            binding.tvTimer3.text =
                "${savedValue33.substring(0, 2)}분 ${savedValue33.substring(3, 5)}초"

            if (getTime3.text.toString().compareTo(binding.tvTimer3.text.toString()) <= 0) {
                timesuccess3 = 1
                routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                timesuccess3 = 2
                routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }

            //            값 받아오면 remove버튼 소환
            binding.btnPlay3.visibility = View.GONE
            binding.btnRemove3.visibility = View.VISIBLE

            showFrameLayout(frameLayout2)
        }

        //        remove3버튼 동작 - 색깔 , 저장된 스탑워치 값 리셋
        binding.btnRemove3.setOnClickListener {

            binding.tvTimer3.setText("")


            timesuccess3 = 0

            // SharedPreferences에서 Editor 생성
            val editor = sharedPref3.edit()

            // times3 키에 해당하는 데이터 제거
            editor.remove("times3")

            // 변경사항을 적용
            editor.commit()

            // UI 업데이트 등 필요한 작업 수행
            routinePackage3.setBackgroundColor(Color.TRANSPARENT)

            binding.btnPlay3.visibility = View.VISIBLE
            binding.btnRemove3.visibility = View.GONE

            savedPref3 = null

            // calculateAndSetTotalTime 함수 호출
            val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
            calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)

        }
//        binding.btnPlay3.setOnClickListener {
//            intent = Intent(this, StopActivity3::class.java)
//            startActivity(intent)
//        }
//        val value3 = intent.getStringExtra("times3")
//
//        val routinePackage3 = findViewById<RelativeLayout>(R.id.routinePackage3)
//
//        val getTime3 = findViewById<TextView>(R.id.getTime3)
//
//        val laterText3 = getTime3.text.toString()
//
//        if (value3 != null && value3.startsWith(laterText3.substring(0, 5))) {
//            routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//        } else {
//            routinePackage3.setBackgroundColor(Color.TRANSPARENT) // 초록색이 아닌 경우 투명 배경으로 초기화
//        }
//
//
//        if (value3 == null) {
//            binding.tvTimer3.text = "소요시간"
//        } else {
//            val value33 = value3.toString()
//            binding.tvTimer3.text = value33.substring(0, 2) + "분 " + value33.substring(3, 5) + "초"
//
//            if (getTime3.text.toString().compareTo(binding.tvTimer3.text.toString()) <= 0) {
//                routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//            } else {
//                routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
//            }
//        }


        // 네번째 루틴 타이머
        binding.btnPlay4.setOnClickListener {
            intent = Intent(this, StopActivity4::class.java)
            intent.putExtra("isGetTime4", binding.getTime4.text.toString())
            startActivity(intent)
        }

// 초기화
        val sharedPref4 = getSharedPreferences("stop4", Context.MODE_PRIVATE)
        savedPref4 = sharedPref4.getString("times4", "")

        val routinePackage4 = findViewById<RelativeLayout>(R.id.routinePackage4)
        val getTime4 = findViewById<TextView>(R.id.getTime4)

// 사용
        if (savedPref4.isNullOrEmpty() || savedPref4!!.length < 5) {
            // 초기화되지 않았거나 유효한 값이 없는 경우
            routinePackage4.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val laterText4 = getTime4.text.toString()

            if (savedPref4!!.startsWith(laterText4.substring(0, 5))) {
                routinePackage4.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                routinePackage4.setBackgroundColor(Color.TRANSPARENT)
            }

            val savedValue44 = savedPref4.toString()
            binding.tvTimer4.text =
                "${savedValue44.substring(0, 2)}분 ${savedValue44.substring(3, 5)}초"

            if (getTime4.text.toString().compareTo(binding.tvTimer4.text.toString()) <= 0) {
                timesuccess4 = 1
                routinePackage4.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                timesuccess4 = 2
                routinePackage4.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }

            //            값 받아오면 remove버튼 소환
            binding.btnPlay4.visibility = View.GONE
            binding.btnRemove4.visibility = View.VISIBLE

            showFrameLayout(frameLayout2)
        }

        //        remove4버튼 동작 - 색깔 , 저장된 스탑워치 값 리셋
        binding.btnRemove4.setOnClickListener {

            binding.tvTimer4.setText("")


            timesuccess4 = 0

            // SharedPreferences에서 Editor 생성
            val editor = sharedPref4.edit()

            // times4 키에 해당하는 데이터 제거
            editor.remove("times4")

            // 변경사항을 적용
            editor.commit()

            // UI 업데이트 등 필요한 작업 수행
            routinePackage4.setBackgroundColor(Color.TRANSPARENT)

            binding.btnPlay4.visibility = View.VISIBLE
            binding.btnRemove4.visibility = View.GONE

            savedPref4 = null

            // calculateAndSetTotalTime 함수 호출
            val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
            calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)

        }
//        binding.btnPlay4.setOnClickListener {
//            intent = Intent(this, StopActivity4::class.java)
//            startActivity(intent)
//        }
//        val value4 = intent.getStringExtra("times4")
//
//        val routinePackage4 = findViewById<RelativeLayout>(R.id.routinePackage4)
//
//        val getTime4 = findViewById<TextView>(R.id.getTime4)
//
//        val laterText4 = getTime3.text.toString()
//
//        if (value4 != null && value4.startsWith(laterText4.substring(0, 5))) {
//            routinePackage3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//        } else {
//            routinePackage3.setBackgroundColor(Color.TRANSPARENT) // 초록색이 아닌 경우 투명 배경으로 초기화
//        }
//
//
//        if (value4 == null) {
//            binding.tvTimer4.text = "소요시간"
//        } else {
//            val value44 = value4.toString()
//            binding.tvTimer4.text = value44.substring(0, 2) + "분 " + value44.substring(3, 5) + "초"
//
//            if (getTime4.text.toString().compareTo(binding.tvTimer4.text.toString()) <= 0) {
//                routinePackage4.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//            } else {
//                routinePackage4.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
//            }
//        }


        // 다섯번째 루틴 타이머
        binding.btnPlay5.setOnClickListener {
            intent = Intent(this, StopActivity5::class.java)
            intent.putExtra("isGetTime5", binding.getTime5.text.toString())
            startActivity(intent)
        }

        // 초기화
        val sharedPref5 = getSharedPreferences("stop5", Context.MODE_PRIVATE)
        savedPref5 = sharedPref5.getString("times5", "")

        val routinePackage5 = findViewById<RelativeLayout>(R.id.routinePackage5)
        val getTime5 = findViewById<TextView>(R.id.getTime5)

// 사용
        if (savedPref5.isNullOrEmpty() || savedPref5!!.length < 5) {
            // 초기화되지 않았거나 유효한 값이 없는 경우
            routinePackage5.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val laterText5 = getTime5.text.toString()

            if (savedPref5!!.startsWith(laterText5.substring(0, 5))) {
                routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                routinePackage5.setBackgroundColor(Color.TRANSPARENT)
            }

            val savedValue55 = savedPref5.toString()
            binding.tvTimer5.text =
                "${savedValue55.substring(0, 2)}분 ${savedValue55.substring(3, 5)}초"

            if (getTime5.text.toString().compareTo(binding.tvTimer5.text.toString()) <= 0) {
                timesuccess5 = 1
                routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                timesuccess5 = 2
                routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            }

            //            값 받아오면 remove버튼 소환
            binding.btnPlay5.visibility = View.GONE
            binding.btnRemove5.visibility = View.VISIBLE

            showFrameLayout(frameLayout2)
        }

        //        remove5버튼 동작 - 색깔 , 저장된 스탑워치 값 리셋
        binding.btnRemove5.setOnClickListener {

            binding.tvTimer5.setText("")


            timesuccess5 = 0

            // SharedPreferences에서 Editor 생성
            val editor = sharedPref5.edit()

            // times5 키에 해당하는 데이터 제거
            editor.remove("times5")

            // 변경사항을 적용
            editor.commit()

            // UI 업데이트 등 필요한 작업 수행
            routinePackage5.setBackgroundColor(Color.TRANSPARENT)

            binding.btnPlay5.visibility = View.VISIBLE
            binding.btnRemove5.visibility = View.GONE

            savedPref5 = null

            // calculateAndSetTotalTime 함수 호출
            val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
            calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)


        }
//        binding.btnPlay5.setOnClickListener {
//            intent = Intent(this, StopActivity5::class.java)
//            startActivity(intent)
//        }
//        val value5 = intent.getStringExtra("times5")
//
//        val routinePackage5 = findViewById<RelativeLayout>(R.id.routinePackage5)
//
//        val getTime5 = findViewById<TextView>(R.id.getTime5)
//
//        val laterText5 = getTime5.text.toString()
//
//        if (value5 != null && value5.startsWith(laterText5.substring(0, 5))) {
//            routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//        } else {
//            routinePackage5.setBackgroundColor(Color.TRANSPARENT) // 초록색이 아닌 경우 투명 배경으로 초기화
//        }
//
//
//        if (value5 == null) {
//            binding.tvTimer5.text = "소요시간"
//        } else {
//            val value55 = value5.toString()
//            binding.tvTimer5.text = value55.substring(0, 2) + "분 " + value5.substring(3, 5) + "초"
//
//            if (getTime5.text.toString().compareTo(binding.tvTimer5.text.toString()) <= 0) {
//                routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//            } else {
//                routinePackage5.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
//            }
//        }


        //총 운동시간 자동 계산
        val savedPrefs = arrayOf(savedPref1, savedPref2, savedPref3, savedPref4, savedPref5)
        calculateAndSetTotalTime(savedPrefs, binding.tvTimerTotal)



//        카운트다운 페이지 이동
        binding.btnTimer.setOnClickListener {
            intent = Intent(this, CountDownActivity::class.java)
            startActivity(intent)
        }




//        세번째 화면
//        운동 관리 페이지 편집 버튼
        var selectedDate:String = ""

        binding.btnEdit.setOnClickListener {
            Toast.makeText(this, "오늘날짜만 편집가능합니다", Toast.LENGTH_SHORT).show()

            binding.Home3.visibility = View.INVISIBLE
            binding.Home2.visibility = View.VISIBLE
        }

//        운동 관리 페이지 삭제 버튼

        binding.btnDel.setOnClickListener {

            val view = View.inflate(this, R.layout.dialog_view, null)


            val builder = AlertDialog.Builder(this)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            dialog.show()

            val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
            val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

            btnConfirm.setOnClickListener {

                deleteData("Write_$selectedDate")
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))


        }


//        binding.btnDel.setOnClickListener {
//
////            삭제할 데이터의 크기를 받아오기위한 삭제할 데이터 변수설정
//            val textValue = binding.showMemo.text.toString()
//
//            //            텍스트가 비어있을 때
//            if (textValue.isBlank()) {
//                Toast.makeText(this@MainActivity, "삭제할 데이터가 없습니다", Toast.LENGTH_SHORT).show()
//            } else {
//                //        삭제 버튼 이벤트 핸들러
//                val eventHandler = object : DialogInterface.OnClickListener {
//                    override fun onClick(p0: DialogInterface?, p1: Int) {
//
//                        //                    Positive Button 클릭 시
//                        if (p1 == DialogInterface.BUTTON_POSITIVE) {
//
//                            //                        삭제 로직
//                            binding.showMemo.setText("")
//                            Toast.makeText(this@MainActivity, "삭제되었습니다", Toast.LENGTH_SHORT).show()
//
//                            //                            Negative Button 클릭 시
//                        } else if (p1 == DialogInterface.BUTTON_NEGATIVE) {
//                            Toast.makeText(this@MainActivity, "취소되었습니다", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }

                //            삭제 버튼 AlertDialog
//                AlertDialog.Builder(this).run {
//                    setTitle("삭제 하시겠습니까?")
//                    setMessage("삭제된 데이터는 복구할 수 없습니다")
//                    setPositiveButton("ㅇㅇ삭제함", eventHandler)
//                    setNegativeButton("ㄴㄴ삭제안함", eventHandler)
//                    show()
//                }
//            }
//        }



//        운동 관리 페이지 달력 불러오기 버튼
        binding.iconCalendar.setOnClickListener {
            // DatePickerDialog를 사용하여 날짜 선택
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).run {
                        calendar.set(selectedYear, selectedMonth, selectedDay)
                        format(calendar.time)
                    }

                    //색깔 초기화
                    binding.form1.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.transparent
                        )
                    )
                    binding.form2.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.transparent
                        )
                    )
                    binding.form3.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.transparent
                        )
                    )
                    binding.form4.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.transparent
                        )
                    )
                    binding.form5.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.transparent
                        )
                    )

                    // 선택된 날짜를 기반으로 파일명 생성
                    val fileName = "Write_$selectedDate"


//                    binding.dayText3.text = selectedDate
                    val selecteDate2 = SimpleDateFormat("yyyy년MM월dd일", Locale.getDefault()).run {
                        calendar.set(selectedYear, selectedMonth, selectedDay)
                        format(calendar.time)
                    }
                    // 선택된 날짜를 TextView에 바인딩
//                    binding.dayText3.text = selectedDate
                    binding.dayText3.text = selecteDate2

                    // 해당 파일에서 데이터를 읽어와 UI에 표시
                    loadDataAndDisplayUI(fileName)
                }, year, month, day)

            datePickerDialog.show()


        }

        binding.btnDone.setOnClickListener {
            binding.Home3.visibility = View.VISIBLE
            binding.Home1.visibility = View.INVISIBLE
            binding.title1.visibility = View.VISIBLE
            binding.homeLine.visibility = View.VISIBLE
            binding.tvToday.visibility = View.VISIBLE
            binding.Schedule.visibility = View.VISIBLE
            binding.btnDone.visibility = View.INVISIBLE
            Toast.makeText(this, "날짜가 변경되었습니다", Toast.LENGTH_SHORT).show()
        }


    }


    fun calculateAndSetTotalTime(savedPrefs: Array<String?>, textView: TextView) {
        // 총 운동 시간 계산
        var totalHours = 0
        var totalMinutes = 0

        for (savedPref in savedPrefs) {
            if (!savedPref.isNullOrEmpty()) {
                totalHours += savedPref.substring(0, 2).toInt()
                totalMinutes += savedPref.substring(3, 5).toInt()
            }
        }

        val totalSeconds = totalHours * 60 + totalMinutes
        val formattedTime = String.format("%02d분 %02d초", totalSeconds / 60, totalSeconds % 60)

        // 포맷팅된 시간을 TextView에 설정
        binding.tvTimerTotal.text = formattedTime

    }

    private fun deleteData(fileName: String) {
        val sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // 삭제할 데이터의 키 값을 사용하여 데이터 삭제

        editor.remove("tvTimerTotal")
        editor.remove("memo")
        editor.remove("timedetect1")
        editor.remove("timedetect2")
        editor.remove("timedetect3")
        editor.remove("timedetect4")
        editor.remove("timedetect5")
        editor.remove("realtime1")
        editor.remove("realtime2")
        editor.remove("realtime3")
        editor.remove("realtime4")
        editor.remove("realtime5")
        binding.showMemo.setText("")
        binding.showRealTime1.setText("")
        binding.showRealTime2.setText("")
        binding.showRealTime3.setText("")
        binding.showRealTime4.setText("")
        binding.showRealTime5.setText("")
        binding.tvShowTimerTotal.setText("")
        binding.form1.setBackgroundColor(Color.TRANSPARENT)
        binding.form2.setBackgroundColor(Color.TRANSPARENT)
        binding.form3.setBackgroundColor(Color.TRANSPARENT)
        binding.form4.setBackgroundColor(Color.TRANSPARENT)
        binding.form5.setBackgroundColor(Color.TRANSPARENT)




        // 변경 사항 저장
        editor.apply()
    }


    private fun cancelNotification() {
        // NotificationManager를 사용하여 알림을 삭제
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11) // 알림 ID를 사용하여 알림 취소
    }

    // 데이터를 읽어와 UI에 표시하는 함수
    private fun loadDataAndDisplayUI(fileName: String) {
        val sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE)

        // 파일에서 데이터 읽기
        val title = sharedPref.getString("title", "")
        val spinner = sharedPref.getString("spinner", "")

        val routin1 = sharedPref.getString("con1", "")
        val routin2 = sharedPref.getString("con2", "")
        val routin3 = sharedPref.getString("con3", "")
        val routin4 = sharedPref.getString("con4", "")
        val routin5 = sharedPref.getString("con5", "")

        val time1 = sharedPref.getString("time1", "")
        val time2 = sharedPref.getString("time2", "")
        val time3 = sharedPref.getString("time3", "")
        val time4 = sharedPref.getString("time4", "")
        val time5 = sharedPref.getString("time5", "")
        val tvTimerTotal = sharedPref.getString("tvTimerTotal", "")

        val memo = sharedPref.getString("memo", "")

        var timesuccess11 = sharedPref.getInt("timedetect1", 0)
        var timesuccess21 = sharedPref.getInt("timedetect2", 0)
        var timesuccess31 = sharedPref.getInt("timedetect3", 0)
        var timesuccess41 = sharedPref.getInt("timedetect4", 0)
        var timesuccess51 = sharedPref.getInt("timedetect5", 0)

        val realTime1 = sharedPref.getString("realtime1", "")
        val realTime2 = sharedPref.getString("realtime2", "")
        val realTime3 = sharedPref.getString("realtime3", "")
        val realTime4 = sharedPref.getString("realtime4", "")
        val realTime5 = sharedPref.getString("realtime5", "")

        // UI에 데이터 연동
        binding.showTitle.text = title
        binding.showSpinner.text = spinner
        binding.showRoutine1.text = routin1
        binding.showRoutine2.text = routin2
        binding.showRoutine3.text = routin3
        binding.showRoutine4.text = routin4
        binding.showRoutine5.text = routin5
        binding.showTime1.text = time1
        binding.showTime2.text = time2
        binding.showTime3.text = time3
        binding.showTime4.text = time4
        binding.showTime5.text = time5
        binding.tvShowTimerTotal.text = tvTimerTotal
        binding.showMemo.text = memo
        binding.getHomeMemo.text = memo
        binding.showRealTime1.text = realTime1
        binding.showRealTime2.text = realTime2
        binding.showRealTime3.text = realTime3
        binding.showRealTime4.text = realTime4
        binding.showRealTime5.text = realTime5

        if (timesuccess11 == 1) {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess11 == 2) {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form1.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess21 == 1) {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess21 == 2) {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess31 == 1) {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess31 == 2) {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form3.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess41 == 1) {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess41 == 2) {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form4.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }

        if (timesuccess51 == 1) {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_light
                )
            )
        } else if (timesuccess51 == 2) {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_light
                )
            )
        } else {
            binding.form5.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        }
//        binding.iconCalendar.setOnClickListener {
//            binding.Home3.visibility = View.INVISIBLE
//            binding.Home1.visibility = View.VISIBLE
//            binding.title1.visibility = View.INVISIBLE
//            binding.homeLine.visibility = View.INVISIBLE
//            binding.tvToday.visibility = View.INVISIBLE
//            binding.Schedule.visibility = View.INVISIBLE
//            binding.btnDone.visibility = View.VISIBLE
//        }
//
//        binding.btnDone.setOnClickListener {
//            binding.Home3.visibility = View.VISIBLE
//            binding.Home1.visibility = View.INVISIBLE
//            binding.title1.visibility = View.VISIBLE
//            binding.homeLine.visibility = View.VISIBLE
//            binding.tvToday.visibility = View.VISIBLE
//            binding.Schedule.visibility = View.VISIBLE
//            binding.btnDone.visibility = View.INVISIBLE
//            Toast.makeText(this, "날짜가 변경되었습니다", Toast.LENGTH_SHORT).show()
//        }


    }
}