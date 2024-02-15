package com.example.cryptohub

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val edtUser = findViewById<EditText>(R.id.edtUser)
        val edtPass = findViewById<EditText>(R.id.edtPass)
        val bttnRegistrar = findViewById<Button>(R.id.bttnRegistrar)
        val admin = SQLAd(this, "Cryptos", null, 1)

        bttnRegistrar.setOnClickListener {
            try {
                val bd = admin.writableDatabase
                val usuario = edtUser.text.toString()
                val clave = edtPass.text.toString()

                val cursor = bd.query("usuario", arrayOf("user"), "user = ?", arrayOf(usuario), null, null, null)

                if (cursor.moveToFirst()) {
                    Toast.makeText(this, "El usuario ya existe.", Toast.LENGTH_SHORT).show()
                    cursor.close()
                    bd.close()
                } else {
                    val registro = ContentValues()
                    registro.put("user", usuario)
                    registro.put("clave", clave)
                    registro.put("bitcoin", 0)
                    registro.put("ethereum", 0)
                    registro.put("ripple", 0)
                    registro.put("litecoin", 0)
                    registro.put("saldo", 0)

                    val ins = bd.insert("usuario", null, registro)

                    if (ins != -1L) {
                        Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Error al registrar el usuario.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    cursor.close()
                    bd.close()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}