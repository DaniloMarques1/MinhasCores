package com.fitz.minhascores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.fitz.minhascores.R
import com.fitz.minhascores.model.Cor

class ColorAdapter(private val context: Context, private val colors: ArrayList<Cor>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return colors.size
    }

    override fun getItem(position: Int): Any {
        return colors[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.color_view_holder, parent, false)

        val color = colors[position]
        val colorNameTxt = view.findViewById<TextView>(R.id.color_name)
        val colorCodeTxt = view.findViewById<TextView>(R.id.color_code)
        val palette = view.findViewById<ImageView>(R.id.palette_color)

        palette.setColorFilter(color.code)

        colorNameTxt.text = color.name
        colorCodeTxt.text = color.toHex()

        return view
    }
}