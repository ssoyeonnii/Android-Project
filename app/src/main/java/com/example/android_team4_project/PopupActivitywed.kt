package com.example.android_team4_project

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.android_team4_project.databinding.ActivityPopupBinding

import com.google.firebase.auth.FirebaseAuth


class PopupActivitywed : AppCompatActivity() {

    private lateinit var binding: ActivityPopupBinding
    private lateinit var userEmail: String
    private lateinit var arrayList: ArrayList<String>
    private lateinit var dialog: Dialog
    private lateinit var spinner: Spinner
    private lateinit var selectedSpinnerValue: String
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        userEmail = user?.uid ?: ""

        dialog = Dialog(this)

        val sharedPrefTitlewed = getSharedPreferences("Routine_Wed$userEmail", Context.MODE_PRIVATE)

        val routine = RoutineData(
            title = sharedPrefTitlewed.getString("title", "") ?: "",
            selectedSpinnerItem = sharedPrefTitlewed.getString("selectedSpinnerItem", "") ?: "",
            con1 = sharedPrefTitlewed.getString("con1", "") ?: "",
            con2 = sharedPrefTitlewed.getString("con2", "") ?: "",
            con3 = sharedPrefTitlewed.getString("con3", "") ?: "",
            con4 = sharedPrefTitlewed.getString("con4", "") ?: "",
            con5 = sharedPrefTitlewed.getString("con5", "") ?: "",

            edm1 = sharedPrefTitlewed.getString("edm1", "") ?: "",
            edm2 = sharedPrefTitlewed.getString("edm2", "") ?: "",
            edm3 = sharedPrefTitlewed.getString("edm3", "") ?: "",
            edm4 = sharedPrefTitlewed.getString("edm4", "") ?: "",
            edm5 = sharedPrefTitlewed.getString("edm5", "") ?: "",

            eds1 = sharedPrefTitlewed.getString("eds1", "") ?: "",
            eds2 = sharedPrefTitlewed.getString("eds2", "") ?: "",
            eds3 = sharedPrefTitlewed.getString("eds3", "") ?: "",
            eds4 = sharedPrefTitlewed.getString("eds4", "") ?: "",
            eds5 = sharedPrefTitlewed.getString("eds5", "") ?: ""

        )
        initializeEditText(binding.edTitle, routine.title)
        routine.con1?.let { initializeEditText(binding.edContent1, it) }
        routine.con2?.let { initializeEditText(binding.edContent2, it) }
        routine.con3?.let { initializeEditText(binding.edContent3, it) }
        routine.con4?.let { initializeEditText(binding.edContent4, it) }
        routine.con5?.let { initializeEditText(binding.edContent5, it) }
        initializeEditText(binding.edm1, routine.edm1.toString())
        initializeEditText(binding.edm2, routine.edm2.toString())
        initializeEditText(binding.edm3, routine.edm3.toString())
        initializeEditText(binding.edm4, routine.edm4.toString())
        initializeEditText(binding.edm5, routine.edm5.toString())
        initializeEditText(binding.eds1, routine.eds1.toString())
        initializeEditText(binding.eds2, routine.eds2.toString())
        initializeEditText(binding.eds3, routine.eds3.toString())
        initializeEditText(binding.eds4, routine.eds4.toString())
        initializeEditText(binding.eds5, routine.eds5.toString())

        binding.spinner.setSelection(getSelectedSpinnerPosition(sharedPrefTitlewed, routine.selectedSpinnerItem))


        binding.btnSaveAll.setOnClickListener {
            //Toast
            Toast.makeText(this,"수요일 루틴이 저장되었습니다.", Toast.LENGTH_SHORT).show()



            val edTitle = binding.edTitle.text.toString()
            val edCon1 = binding.edContent1.text.toString()
            val edCon2 = binding.edContent2.text.toString()
            val edCon3 = binding.edContent3.text.toString()
            val edCon4 = binding.edContent4.text.toString()
            val edCon5 = binding.edContent5.text.toString()

            val edm1 = binding.edm1.text.toString().toIntOrNull() ?: 0
            val edm2 = binding.edm2.text.toString().toIntOrNull() ?: 0
            val edm3 = binding.edm3.text.toString().toIntOrNull() ?: 0
            val edm4 = binding.edm4.text.toString().toIntOrNull() ?: 0
            val edm5 = binding.edm5.text.toString().toIntOrNull() ?: 0

            val eds1 = binding.eds1.text.toString().toIntOrNull() ?: 0
            val eds2 = binding.eds2.text.toString().toIntOrNull() ?: 0
            val eds3 = binding.eds3.text.toString().toIntOrNull() ?: 0
            val eds4 = binding.eds4.text.toString().toIntOrNull() ?: 0
            val eds5 = binding.eds5.text.toString().toIntOrNull() ?: 0

            // 스피너에서 선택된 항목의 값을 가져오기
            val selectedSpinnerItem = binding.spinner.selectedItem.toString()

            // RoutineData 객체 업데이트
            val routine = RoutineData(
                title = edTitle,
                selectedSpinnerItem = selectedSpinnerItem,
                con1 = edCon1,
                con2 = edCon2,
                con3 = edCon3,
                con4 = edCon4,
                con5 = edCon5,
                edm1 = edm1.toString(),
                edm2 = edm2.toString(),
                edm3 = edm3.toString(),
                edm4 = edm4.toString(),
                edm5 = edm5.toString(),
                eds1 = eds1.toString(),
                eds2 = eds2.toString(),
                eds3 = eds3.toString(),
                eds4 = eds4.toString(),
                eds5 = eds5.toString()
            )

            // RoutineData 객체를 사용하여 XML 데이터 생성
            val xmlData = generateXmlData(routine,"Wed")

            // userEmail을 이용하여 StorageManager를 초기화
            val storageManager = StorageManager(this)

            // StorageManager를 사용하여 XML 파일 업로드

            storageManager.uploadXmlFile(xmlData,"Wed")

            // SharedPreferences를 통해 데이터 저장
            val userUid = FirebaseAuth.getInstance().uid
            val sharedPrefTitlewed =
                getSharedPreferences("Routine_Wed$userUid", Context.MODE_PRIVATE)
            val editor = sharedPrefTitlewed.edit()

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

        binding.btnCancel.setOnClickListener {
            // 이동할 액티비티코드
            val intent = Intent(this, MyActivity::class.java)
            startActivity(intent)
        }

        //      버튼클릭시 마이루틴으로 이동
        binding.btnMypage.setOnClickListener {
            // 이동할 액티비티코드
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        // 스피너 객체 생성
        spinner = binding.spinner

//        // 스피너 클릭 시 다이얼로그 띄우기
//        spinner.setOnTouchListener { _, _ ->
//            showSearchableSpinnerDialog()
//            false
//        }
        // 스피너 클릭 시 다이얼로그 띄우기
        spinner.setOnTouchListener { _, _ ->
            if (dialog == null) {
                // 다이얼로그가 초기화되지 않았다면 초기화 후 보여주기
                showSearchableSpinnerDialog()
            } else if (!dialog!!.isShowing) {
                // 다이얼로그가 초기화되어 있고 보여지지 않는 상태라면 보여주기
                showSearchableSpinnerDialog()
            }
            true
        }
        // 초기화
        arrayList = arrayListOf(
            "선택하기",
            "필라테스",
            "맨몸운동",
            "요가",
            "러닝",
            "로잉운동",
            "사이클링",
            "스탭퍼운동",
            "하이킹",
            "웨이트"
        )

        // 드롭다운 메뉴에 표시할 항목들
        val items =
            arrayOf("선택하기", "필라테스", "맨몸운동", "요가", "러닝", "로잉운동", "사이클링", "스탭퍼운동", "하이킹", "웨이트")

        // ArrayAdapter를 사용하여 드롭다운 메뉴에 항목들을 연결
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter


        val items2 =
            arrayOf("선택하기", "필라테스", "맨몸운동", "요가", "러닝", "로잉운동", "사이클링", "스탭퍼운동", "하이킹", "웨이트")
        // 저장된 값을 불러와서 스피너에 대입
        val savedSpinnerItem = sharedPrefTitlewed.getString("selectedSpinnerItem", "")
        if (items2.contains(savedSpinnerItem)) {
            val positionInAdapter = items2.indexOf(savedSpinnerItem)
            binding.spinner.setSelection(positionInAdapter)
        }

    }

    //    업로드할 파일에 저장될 값 설정
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


    private fun initializeEditText(editText: EditText, value: String) {
        editText.setText(value)
    }

    private fun getSelectedSpinnerPosition(sharedPrefs: SharedPreferences, savedValue: String): Int {
        val items = arrayOf("선택하기", "필라테스", "맨몸운동", "요가", "러닝", "로잉운동", "사이클링", "스탭퍼운동", "하이킹", "웨이트")
        return items.indexOf(savedValue).coerceAtLeast(0)
    }
    private fun showSearchableSpinnerDialog() {
        // dialog 초기화
        dialog = Dialog(this)

        // dialog set
        dialog!!.setContentView(R.layout.dialog_searchable_spinner)
        dialog!!.window?.setLayout(650, 800)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialog!!.show()

        val editText: EditText = dialog!!.findViewById(R.id.edit_text)
        val listView: ListView = dialog!!.findViewById(R.id.list_view)

        val dialogAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)

        // 어댑터 설정
        listView.adapter = dialogAdapter

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dialogAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            // 선택한 값을 프로퍼티에 설정
            selectedSpinnerValue = dialogAdapter.getItem(position).toString()


            val positionInAdapter = arrayList.indexOf(selectedSpinnerValue)
            // 스피너에 선택된 항목 대입
//            val positionInAdapter = dialogAdapter.getPosition(selectedSpinnerValue)

            if (positionInAdapter != -1) {
                spinner.setSelection(positionInAdapter)
            }
            // 사용자가 항목 선택하면 다이얼로그 닫음
            dialog!!.dismiss()

        }
    }
}