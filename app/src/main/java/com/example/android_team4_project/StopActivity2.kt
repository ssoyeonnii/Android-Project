package com.example.android_team4_project
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.android_team4_project.databinding.ActivityStop2Binding


class StopActivity2 : AppCompatActivity() {
    var initTime = 0L
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStop2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart2.setOnClickListener {
            binding.chronometer2.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer2.start()
            binding.btnStop2.isEnabled = true
            binding.btnReset2.isEnabled = true
            binding.btnStart2.isEnabled = false
            binding.btnSave2.visibility = View.INVISIBLE
        }

//        binding.btnStop.text = "Stop"
        binding.btnStop2.setOnClickListener {
            pauseTime = binding.chronometer2.base - SystemClock.elapsedRealtime()
            binding.chronometer2.stop()
            binding.btnStart2.isEnabled = true
            binding.btnStop2.isEnabled = false
            binding.btnReset2.isEnabled = true
            binding.btnSave2.isEnabled = true
            binding.btnSave2.visibility = View.VISIBLE
        }

        binding.btnReset2.setOnClickListener {
//            binding.btnReset.text = "Reset"
            pauseTime = 0L
            binding.chronometer2.base = SystemClock.elapsedRealtime()
            binding.chronometer2.stop()
            binding.btnStart2.isEnabled = true
            binding.btnStop2.isEnabled = false
            binding.btnReset2.isEnabled = false
            binding.btnSave2.visibility = View.INVISIBLE
        }

        //getTime1와 chronometer값이 같아지면 notification뜨게하기
        binding.chronometer2.setOnChronometerTickListener{

            // Mainactivity에서 stopActivity1 열때 보냈던 사용자가 지정한 루틴 시간 값 받아오기
            val isGetTime2 = intent.getStringExtra("isGetTime2")

            // 정규식을 사용하여 "분"과 "초"를 없애고, ":"로 분과 초를 구분하여 합치기
            val modifiedText = isGetTime2.toString().replace(Regex("[^0-9]"), "").chunked(2).joinToString(":") { it }

            val elapsedMillis = SystemClock.elapsedRealtime() - binding.chronometer2.base
            val elapsedSeconds = elapsedMillis / 1000

            // TextView 업데이트 또는 특정 시간에 도달하면 알림 등

            // 경과된 시간을 분과 초로 변환
            val elapsedMinutes = elapsedSeconds / 60
            val remainingSeconds = elapsedSeconds % 60

            val currentTime = String.format("%02d:%02d", elapsedMinutes, remainingSeconds)

            if(modifiedText == currentTime){
                notiAlarm()
//                Toast.makeText(this,"good" , Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnSave2.setOnClickListener {
            pauseTime = binding.chronometer2.base - SystemClock.elapsedRealtime()
            binding.chronometer2.stop()
            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("times2", binding.chronometer2.text.toString())
            startActivity(intent)

            val sharePref2 = getSharedPreferences("stop2", Context.MODE_PRIVATE)
            val editor2 = sharePref2.edit()

            editor2.putString("times2", binding.chronometer2.text.toString())
            editor2.apply()
        }
    }
    private fun notiAlarm() {

//    getSystemService(서비스) : 안드로이드 시스템에서 동작하고 있는 서비스 중 지정한 서비스를 가져옴
//    getSystemService() 메소드를 사용하여 NotificationManager 타입의 객체 가져오기
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//    NotificationCompat 타입의 객체를 저장할 변수 선언
        val builder: NotificationCompat.Builder

//    API 26부터 채널이 추가 되어 버전에 따라 사용 방식을 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "one-channel"
            val channelName = "My Channel One"
//      채널 객체 생성
            val channel = NotificationChannel(
                channelId,
                channelName,
//        알림 등급 설정
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.description = "My Channel One description"
            channel.setShowBadge(true)

//      알림 시 라이트 사용
            channel.enableLights(true)
            channel.lightColor = Color.RED

//      지정한 채널 정보를 통해서 채널 생성
            manager.createNotificationChannel(channel)

//      NotificationCompat 타입의 객체 생성
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

//    스테이스터창 알림 화면 설정
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("알림")
        builder.setContentText("루틴 성공!")

//    NotificationManager를 사용하여 스테이터스창에 알림창 출력
        manager.notify(11, builder.build())
    }

}
