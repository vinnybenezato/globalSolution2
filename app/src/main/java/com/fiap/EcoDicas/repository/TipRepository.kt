package com.fiap.EcoDicas.repository

import android.content.ContentValues
import android.content.Context
import com.fiap.EcoDicas.database.TipDatabaseHelper
import com.fiap.EcoDicas.model.Tip

class TipRepository (context: Context) {
    private val dbHelper = TipDatabaseHelper(context)

    fun insertTip(tip: Tip): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TipDatabaseHelper.COLUMN_TITLE, tip.title)
            put(TipDatabaseHelper.COLUMN_DESCRIPTION, tip.description)
            put(TipDatabaseHelper.COLUMN_LINK, tip.link)
            put(TipDatabaseHelper.COLUMN_CURIOSITY, tip.curiosity)
        }
        return db.insert(TipDatabaseHelper.TABLE_NAME, null, values)
    }

    fun listTips(): List<Tip> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TipDatabaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val tips = mutableListOf<Tip>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(TipDatabaseHelper.COLUMN_TITLE))
                val description = getString(getColumnIndexOrThrow(TipDatabaseHelper.COLUMN_DESCRIPTION))
                val link = getString(getColumnIndexOrThrow(TipDatabaseHelper.COLUMN_LINK))
                val curiosity = getString(getColumnIndexOrThrow(TipDatabaseHelper.COLUMN_CURIOSITY))
                tips.add(Tip(title, description, link, curiosity))
            }
        }
        cursor.close()
        return tips
    }
}
