package com.example.submissionwahyu.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.submissionwahyu.R
import com.example.submissionwahyu.data.receiver.AlarmReceiver
//import com.example.submissionwahyu.data.reminder.DailyReminder
import com.example.submissionwahyu.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    //private lateinit var reminder: DailyReminder
    private lateinit var mSharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "pref_name"
        private const val DAILY = "daily"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSettingUI()
        setChangeLanguage()
        setReminder()
        setSettingActionBar()
    }

    private fun setChangeLanguage() {
        binding.btnChangeLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    private fun setReminder() {
        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setChecked()
        binding.apply {
            btnSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmReceiver.setRepetingAlarm(
                        this@SettingActivity,
                        AlarmReceiver.TYPE_DAILY,
                        getString(R.string.content_text)
                    )
                } else {
                    alarmReceiver.cancelAlarm(this@SettingActivity)
                }
                saveChange(isChecked)
            }
        }

    }

    private fun setChecked() {
        binding.apply {
            btnSwitch.isChecked = mSharedPreferences.getBoolean(DAILY, false)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }


    /*private fun setSettingUI() {
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
    }*/

    private fun setSettingActionBar() {
        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.setting)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    /*private fun saveReminder(saveReminderState: Boolean) {
        val reminderPref = ReminderPreference(this)
        reminder = DailyReminder()
        reminder.isReminded = saveReminderState
        reminderPref.setDailyReminder(reminder)
    }*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}