package com.example.android_team4_project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.NotificationCompat
import com.example.android_team4_project.databinding.ActivityCountDownBinding


class CountDownActivity : AppCompatActivity() {

    lateinit var countDownTimer: CountDownTimer
    lateinit var binding: ActivityCountDownBinding

    var timeRunning = false //타이머 실행 상태
    var firstState = false //타이머 실행 처음인지 아닌지

    var time = 0L //타이머 시간
    var tempTime = 0L //타이머 임시 시간

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountDownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //시작버튼
        binding.btnStartCount.setOnClickListener {
            viewMode("start")
            startStop()
        }
        //정지버튼
        binding.btnStopCount.setOnClickListener {
            startStop()
        }

        //취소버튼
        binding.btnCancelCount.setOnClickListener {
            viewMode("cancel")
            startStop()
        }

        //알람 삭제
        cancelNotification()


        }



    private fun viewMode(mode: String) {
        // 상태 : 처음
        firstState = true

        if (mode == "start") { //타이머 모드
            binding.settingLayout.visibility = View.GONE //사라짐
            binding.timerLayout.visibility = View.VISIBLE //보여짐
        } else {//설정모드
            binding.settingLayout.visibility = View.VISIBLE //사라짐
            binding.timerLayout.visibility = View.GONE //보여짐
        }
    }

    //타이머 실행 상태에 따른 시작 & 정지
    private fun startStop() {
        if (timeRunning) { //실행 중이면 정지
            stopTimer()
        } else { //정지면 실행
            startTimer()
        }
    }

//    타이머 실행
    private fun startTimer() {

//        처음 실행이면 입력값을 타이머 설정값으로 사용
        if (firstState) {
            val sHour = binding.hourEdit.text.toString()
            val sMin = binding.minEdit.text.toString()
            val sSec = binding.secEdit.text.toString()

            time =
                (sHour.toLong()) * 3600000 + (sMin.toLong() * 60000) + (sSec.toLong() * 1000) + 1000
        } else { // 정지 후 이어서 시작이면 기존 값 사용하기
            time = tempTime
        }

//        타이머 실행
        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempTime = millisUntilFinished
                //타이머 업데이트
                updateTime()
            }

            override fun onFinish() {
                notiAlarm()
//                //끝나면 소리알림
//                val notification: Uri =
//                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//                val ringtone = RingtoneManager.getRingtone(this@CountDownActivity, notification)
//                ringtone.play()

            }
        }.start()

        //정지 버튼 텍스트
        binding.btnStopCount.text = "일시 정지"
        //타이머 상태 : 실행
        timeRunning = true
        //처음아님
        firstState = false
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

//      알림 동작 시 사용할 음원 지정
//      안드로이드 기본 알림 음원 정보 가져오기
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

//      알림 채널에 사용할 음원 설정
            channel.setSound(uri, audioAttributes)
//      알림 시 라이트 사용
            channel.enableLights(true)
            channel.lightColor = Color.RED
//      알림 시 진동 사용
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 100, 200)

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
        builder.setContentText("타이머 종료")

//    화면 전환을 위한 Intent를 저장할 리스트 변수 생성
        val mainIntent = Intent(this, CountDownActivity::class.java)
//    PendingIntent를 통해서 안드로이드 시스템에 이벤트 요청 처리 등록
//    getActivity()를 통해서 지정한 앱의 화면으로 전환 요청
        var eventPendingIntent =
            PendingIntent.getActivity(this, 30, mainIntent, PendingIntent.FLAG_MUTABLE)

//    알림의 setContentIntent()를 사용하여 PendingIntent 추가하고 지정한 이벤트가 동작하도록 함
//    builder.setContentIntent(eventPendingIntent)

//    알림에는 액션 버튼을 3개까지 추가할 수 있음
//    알림에 액션을 추가
        builder.addAction(
//      매개변수는 아이콘, 액션버튼 제목, PendingIntent 객체 순서
            NotificationCompat.Action.Builder(
                android.R.drawable.stat_notify_more,
                "확인",
                eventPendingIntent
            ).build()
        )

//    NotificationManager를 사용하여 스테이터스창에 알림창 출력
        manager.notify(11, builder.build())
    }

    //    Notification 삭제
    private fun cancelNotification() {
        // NotificationManager를 사용하여 알림을 삭제
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11) // 알림 ID를 사용하여 알림 취소
    }


    //    타이머 정지
    private fun stopTimer() {
        //타이머 취소
        countDownTimer.cancel()

        //타이머 상태 : 정지
        timeRunning = false

        //정지버튼 텍스트
        binding.btnStopCount.text = "계속"
    }

    //    타이머 업데이트
    private fun updateTime() {
        val hour = tempTime / 3600000
        val min = tempTime % 3600000 / 60000
        val sec = tempTime % 3600000 % 60000 / 1000

        //시간 추가
        var timeLeftText = "$hour : "


        //분이 10보다 작으면 0붙이기
        if (min < 10) timeLeftText += "0"

        //분 추가
        timeLeftText += "$min : "

        //초가 10보다 작으면 0붙임
        if (sec < 10) timeLeftText += "0"

        //초 추가
        timeLeftText += "$sec"

        //타이머 텍스트 보여주기
        binding.timerText.text = timeLeftText

    }

}