package com.fitz.minhascores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.fitz.minhascores.adapter.ColorAdapter
import com.fitz.minhascores.dao.ColorDao
import com.fitz.minhascores.model.Cor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listViewColors: ListView
    private lateinit var addColorBtn: FloatingActionButton
    private lateinit var colors: ArrayList<Cor>

    private val colorDao = ColorDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.title = "Home"

        listViewColors = findViewById(R.id.colors_list)
        addColorBtn = findViewById(R.id.add_color_btn)

        addColorBtn.setOnClickListener {
            val intent = Intent(this, AddColorActivity::class.java)
            startActivityForResult(intent, ADD_RESULT_CODE)
        }
        colors = colorDao.findAll()
        //val colors = arrayListOf(Color("Red", 31313), Color("Blue", 31313))
        val colorAdapter = ColorAdapter(this, colors)
        listViewColors.adapter = colorAdapter
        listViewColors.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val cor = colors[position]
            this.editColor(cor)
        }

        listViewColors.onItemLongClickListener = ColorsListLongClick()
    }

    private fun editColor(color: Cor) {
        val intent = Intent(this, AddColorActivity::class.java)
        intent.putExtra("cor", color)
        startActivityForResult(intent, EDIT_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_RESULT_CODE) {
                data?.let {
                    val cor = it.getSerializableExtra("color") as Cor
                    colors.add(cor)
                    colorDao.save(cor)
                    (listViewColors.adapter as ColorAdapter).notifyDataSetChanged()
                }
            } else if (requestCode == EDIT_RESULT_CODE) {
                data?.let {
                    val cor = it.getSerializableExtra("color") as Cor
                    for (c in colors) {
                        if (c.id == cor.id) {
                            cor.name = c.name
                            cor.code = c.code
                            (listViewColors.adapter as ColorAdapter).notifyDataSetChanged()
                            colorDao.update(cor)
                            break
                        }
                    }
                }
            }
        }
    }

    inner class ColorsListLongClick: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ): Boolean {
            val cor = this@MainActivity.colors[position]
            this@MainActivity.colors.remove(cor)
            this@MainActivity.colorDao.removeById(cor.id)
            (this@MainActivity.listViewColors.adapter as ColorAdapter).notifyDataSetChanged()
            Toast.makeText(this@MainActivity, "Cor removida com sucesso", Toast.LENGTH_SHORT).show()
            return true
        }

    }

    companion object {
        const val ADD_RESULT_CODE = 1
        const val EDIT_RESULT_CODE = 2
    }
}