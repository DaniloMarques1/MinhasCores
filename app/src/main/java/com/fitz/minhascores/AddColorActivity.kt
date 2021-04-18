package com.fitz.minhascores

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.fitz.minhascores.model.Cor

class AddColorActivity : AppCompatActivity() {
    private var cor: Cor = Cor("", 0)

    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button

    private lateinit var seekBarVermelho: SeekBar
    private lateinit var seekBarVerde: SeekBar
    private lateinit var seekBarAzul: SeekBar

    private lateinit var currentColorBtn: Button
    private lateinit var edtColorName: EditText

    private var vermelhoCor: Int = 0
    private var verdeCor: Int = 0
    private var azulCor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_color)
        this.title = "Salvar cor"

        edtColorName = this.findViewById(R.id.edt_color_name)
        btnCancel = this.findViewById(R.id.btn_cancel)
        btnSave = this.findViewById(R.id.btn_save)

        seekBarVermelho = this.findViewById(R.id.seekbar_vermelho)
        seekBarVerde = this.findViewById(R.id.seekbar_verde)
        seekBarAzul = this.findViewById(R.id.seekbar_azul)

        currentColorBtn = this.findViewById(R.id.btn_current_color)


        btnCancel.setOnClickListener {
            this.finish()
        }
        btnSave.setOnClickListener {
            this.addColor()
        }

        seekBarVermelho.setOnSeekBarChangeListener(VermelhoSeekBarChanged())
        seekBarVerde.setOnSeekBarChangeListener(VerdeSeekBarChanged())
        seekBarAzul.setOnSeekBarChangeListener(AzulSeekBarChanged())

        intent?.let {
            if (it.hasExtra("cor")) {
                cor = it.getSerializableExtra("cor") as Cor
                edtColorName.setText(cor.name)
                currentColorBtn.setBackgroundColor(cor.code)

                vermelhoCor = Color.red(cor.code)
                azulCor = Color.blue(cor.code)
                verdeCor = Color.green(cor.code)

                seekBarVermelho.progress = vermelhoCor
                seekBarVerde.progress = verdeCor
                seekBarAzul.progress = azulCor
                updateBackgroundColor()
            }
        }
    }

    private fun addColor() {
        cor.name = edtColorName.text.toString()
        val intent = Intent()
        intent.putExtra("color", cor)
        setResult(RESULT_OK, intent)
        Toast.makeText(this, "Cor salva com sucesso", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    private fun updateBackgroundColor() {
        val code = Color.rgb(vermelhoCor, verdeCor, azulCor)
        cor.code = code
        this.currentColorBtn.setBackgroundColor(code)
        this.currentColorBtn.text = cor.toHex()
    }

    inner class VermelhoSeekBarChanged: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            vermelhoCor = progress
            this@AddColorActivity.updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    inner class VerdeSeekBarChanged: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            verdeCor = progress
            this@AddColorActivity.updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

    inner class AzulSeekBarChanged: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            azulCor = progress
            this@AddColorActivity.updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

}