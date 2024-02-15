package com.example.cryptohub

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Fondos : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fondos)

        val texto = findViewById<EditText>(R.id.txtnmTexto)
        val b10 = findViewById<Button>(R.id.bttn10)
        val b20 = findViewById<Button>(R.id.bttn20)
        val b50 = findViewById<Button>(R.id.bttn50)
        val b100 = findViewById<Button>(R.id.bttn100)
        var fondos = intent.getStringExtra("fondos").toString().toInt()
        val añadir = findViewById<Button>(R.id.bttnAñadirFondos)

        b10.setOnClickListener {
            texto.setText("10")
        }
        b20.setOnClickListener {
            texto.setText("20")
        }
        b50.setOnClickListener {
            texto.setText("50")
        }
        b100.setOnClickListener {
            texto.setText("100")
        }

        añadir.setOnClickListener {
            val admin = SQLAd(this, "Cryptos", null, 1)
            val bd = admin.readableDatabase
            val usu = intent.getStringExtra("user")
            val cantidad = texto.text.toString().toInt()
            val valores = ContentValues()
            valores.put("saldo", fondos + cantidad)
            bd.update("usuario", valores, "user = ?", arrayOf(usu))
            bd.close()
            setResult(RESULT_OK)
            finish()
        }
    }
}