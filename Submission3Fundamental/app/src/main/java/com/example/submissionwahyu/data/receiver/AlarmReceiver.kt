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
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_DAILY = "Daily Reminder"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_MESSAGE = "extra_message"
        const val CHANNEL_ID = "channe_id"
        const val CHANNEL_NAME = "github name"
        const val NOTIF_ID = 1

        private const val SET_TIME = "18:55"
        private const val ID_DAILY = 101
    }
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        showNotification(context, message)
    }

    private fun showNotification(mContext: Context, message: String?) {
        /*val intent = Intent(mContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/

        val intent = mContext.packageManager.getLaunchIntentForPackage("com.example.submissionwahyu")

        val pendingIntent = PendingIntent.getActivity(
            mContext, 0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )

        val notifManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(mContext.resources.getString(R.string.content_title))
            .setContentText(mContext.resources.getString(R.string.content_text))
            .setSubText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifchannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notifManager.createNotificationChannel(notifchannel)
        }

        val notification = builder.build()
        notifManager.notify(NOTIF_ID, notification)

    }

    fun setRepetingAlarm(context: Context, type: String, message: String) {
        //val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmManager = context.getSystemService<AlarmManager>()

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArray = SET_TIME.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, PendingIntent.FLAG_ONE_SHOT)

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, R.string.alarm_on, Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        //val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmManager = context.getSystemService<AlarmManager>()

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)

        Toast.makeText(context, R.string.alarm_off, Toast.LENGTH_SHORT).show()
    }

    /*companion object {
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
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, R.string.alarm_off, Toast.LENGTH_SHORT).show()
    }*/
}