package com.fiap.EcoDicas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fiap.EcoDicas.model.Tip

class TipDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tips.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tips"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LINK = "link"
        const val COLUMN_CURIOSITY = "curiosity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_LINK TEXT,
                $COLUMN_CURIOSITY TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTip(tip: Tip): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, tip.title)
        values.put(COLUMN_DESCRIPTION, tip.description)
        values.put(COLUMN_LINK, tip.link)
        values.put(COLUMN_CURIOSITY, tip.curiosity)

        return db.insert(TABLE_NAME, null, values)
    }

    fun listTips(): List<Tip> {
        val tips = mutableListOf<Tip>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val link = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK))
                val curiosity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURIOSITY))

                tips.add(Tip(title, description, link, curiosity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tips
    }
}
