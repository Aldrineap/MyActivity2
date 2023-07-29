package com.alarcon.myprojectlocator

import android.provider.BaseColumns

object DatabaseContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_FULL_NAME = "fullName"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }
}