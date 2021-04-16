package com.example.submissionwahyu.data.reminder

import android.content.Context
import androidx.core.content.edit

class ReminderPreference(context: Context) {
    companion object{
        const val PREF_REMINDER = "pref_reminder"
        const val REMINDER = "reminder"
    }

    private val preference = context.getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE)

    fun setDailyReminder(value: DailyReminder){
        preference.edit {
            putBoolean(REMINDER, value.isReminded)
        }
    }

    fun getDailyReminder(): DailyReminder{
        val model = DailyReminder()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }

}