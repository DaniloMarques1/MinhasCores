package com.fitz.minhascores.dao

import android.content.ContentValues
import android.content.Context
import com.fitz.minhascores.database.SqlHelper
import com.fitz.minhascores.model.Cor

class ColorDao(private val context: Context) {
    private val db: SqlHelper = SqlHelper(this.context)

    fun save(color: Cor) {
        val cv = ContentValues()
        cv.put("name", color.name)
        cv.put("code", color.code)
        db.writableDatabase.insert("colors", null, cv)
    }

    fun findAll(): ArrayList<Cor> {
        val colors = ArrayList<Cor>()
        val sql = "select * from colors order by name"
        val cursor = db.readableDatabase.rawQuery(sql, null)
        cursor.moveToFirst()

        for (i in 1..cursor.count) {
            val color = Cor(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
            colors.add(color)

            cursor.moveToNext()
        }
        cursor.close()

        return colors
    }

    fun update(cor: Cor) {
        val where = "id=${cor.id}"
        val cv = ContentValues()
        cv.put("name", cor.name)
        cv.put("code", cor.code)
        db.writableDatabase.update("colors", cv, where, null)
    }

    fun removeById(id: Int) {
        val where = "id=${id}"
        db.writableDatabase.delete("colors", where, null)
    }
}