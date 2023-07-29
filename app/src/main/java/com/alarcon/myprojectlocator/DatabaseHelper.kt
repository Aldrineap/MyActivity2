package com.alarcon.myprojectlocator

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyProjectLocator.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FULL_NAME = "fullName"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the users table
        val createTableQuery = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_FULL_NAME TEXT NOT NULL," +
                "$COLUMN_EMAIL TEXT NOT NULL," +
                "$COLUMN_PASSWORD TEXT NOT NULL" +
                ");"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }


    fun addUser(user: User): Long {
            val values = ContentValues().apply {
            put(COLUMN_FULL_NAME, user.fullName)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
        }
        return writableDatabase.insert(TABLE_USERS, null, values)
    }
    fun getUserByEmailAndPassword(email: String, password: String): User? {
        val db = this.readableDatabase
        val projection = arrayOf(
            DatabaseContract.UserEntry.COLUMN_ID,
            DatabaseContract.UserEntry.COLUMN_FULL_NAME,
            DatabaseContract.UserEntry.COLUMN_EMAIL,
            DatabaseContract.UserEntry.COLUMN_PASSWORD
        )
        val selection = "${DatabaseContract.UserEntry.COLUMN_EMAIL} = ? AND ${DatabaseContract.UserEntry.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(
            DatabaseContract.UserEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_ID)
            val userNameIndex = cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_FULL_NAME)
            val userEmailIndex = cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_EMAIL)

            if (idIndex != -1 && userNameIndex != -1 && userEmailIndex != -1) {
                val id = cursor.getInt(idIndex)
                val userName = cursor.getString(userNameIndex)
                val userEmail = cursor.getString(userEmailIndex)
                user = User(id, userName, userEmail, password)
            }
        }

        cursor.close()
        db.close()
        return user
    }
    fun authenticateLogin(email: String, password: String): Boolean {

        val user = getUserByEmailAndPassword(email, password)
        return user != null
    }

    fun updatePassword(email: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_PASSWORD, newPassword)
        val result = db.update(TABLE_USERS, contentValues, "$COLUMN_EMAIL = ?", arrayOf(email))
        db.close()
        return result != -1
    }

}
