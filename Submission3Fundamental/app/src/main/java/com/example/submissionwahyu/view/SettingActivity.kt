package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.submissionwahyu.R
import com.example.submissionwahyu.data.receiver.AlarmReceiver
import com.example.submissionwahyu.data.reminder.DailyReminder
import com.example.submissionwahyu.data.reminder.ReminderPreference
import com.example.submissionwahyu.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reminder: DailyReminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangeLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        binding.apply {
            val reminderPref = ReminderPreference(this@SettingActivity)
            btnSwitch.isChecked = reminderPref.getDailyReminder().isReminded

            alarmReceiver = AlarmReceiver()

            btnSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    saveReminder(true)
                    alarmReceiver.setRepeating(this@SettingActivity, "Repeating Alarm","09:00", "Github Reminder")
                }else{
                    saveReminder(false)
                    alarmReceiver.cancelAlarm(this@SettingActivity)
                }
            }
        }

        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.setting)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun saveReminder(saveReminderState: Boolean) {
        val reminderPref = ReminderPreference(this)
        reminder = DailyReminder()
        reminder.isReminded = saveReminderState
        reminderPref.setDailyReminder(reminder)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}