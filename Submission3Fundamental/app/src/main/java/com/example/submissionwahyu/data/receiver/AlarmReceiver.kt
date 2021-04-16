package com.example.submissionwahyu.data.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.submissionwahyu.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "channe_id"
        const val CHANNEL_NAME = "github name"
        const val NOTIFICATION_ID = 1
        const val TIME_FORMAT = "HH:mm"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_MESSAGE = "extra_message"
        const val REPEAT_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.example.submissionwahyu")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.content_text))
            .setSubText(context.resources.getString(R.string.sub_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(CHANNEL_ID)
            notifManager.createNotificationChannel(notifChannel)
        }

        val notif = builder.build()
        notifManager.notify(NOTIFICATION_ID, notif)
    }

    fun setRepeating(context: Context, type: String, time: String, message: String) {
        if (isInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService<AlarmManager>()

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        intent.putExtra(EXTRA_MESSAGE, message)

        val timeSplit = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, R.string.alarm_on, Toast.LENGTH_SHORT).show()
    }

    private fun isInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(time)
            false
        } catch (e: ParseException) {
            true
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val repeatId = REPEAT_ID
        val pendingIntent = PendingIntent.getBroadcast(context, repeatId, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, R.string.alarm_off, Toast.LENGTH_SHORT).show()
    }
}