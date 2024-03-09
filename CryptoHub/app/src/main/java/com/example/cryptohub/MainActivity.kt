package com.example.cryptohub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtUser = findViewById<EditText>(R.id.edtUser)
        val edtPass = findViewById<EditText>(R.id.edtPass)
        val bttnEntrar = findViewById<Button>(R.id.bttnRegistrar)
        val botonFin = findViewById<Button>(R.id.bttnFin)
        val enlace = findViewById<TextView>(R.id.txtvwCuenta)

        enlace.setOnClickListener {
            val intento = Intent(this, Registro::class.java)
            startActivity(intento)
        }

        bttnEntrar.setOnClickListener {
            val admin = SQLAd(this, "Cryptos", null, 1)
            val bd = admin.readableDatabase
            val usuario = edtUser.text.toString()
            val clave = edtPass.text.toString()

            val cursor = bd.query("usuario", arrayOf("user", "clave"),
                "user = ? AND clave = ?", arrayOf(usuario, clave),
                null, null, null)

            if (cursor.moveToFirst()) {
                val intento = Intent(this, Inicio::class.java)
                intento.putExtra("user", usuario)
                startActivity(intento)
            } else {
                Toast.makeText(
                    this,
                    "El usuario o la contraseña no es correcto",
                    Toast.LENGTH_SHORT
                ).show()
            }

            edtUser.text.clear()
            edtPass.text.clear()
            cursor.close()
            bd.close()
        }
        botonFin.setOnClickListener {
            finish()
        }
    }
}
