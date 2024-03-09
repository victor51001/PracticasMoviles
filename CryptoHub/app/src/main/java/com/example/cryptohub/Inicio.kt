package com.example.cryptohub

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val botonAñadirFondos = findViewById<Button>(R.id.bttnAñadir)
        val botonSalir = findViewById<Button>(R.id.bttnSalir)
        val textFondos = findViewById<TextView>(R.id.txtvwFondos)
        val textCB = findViewById<TextView>(R.id.txtvwCB)
        val textCE = findViewById<TextView>(R.id.txtvwCE)
        val textCR = findViewById<TextView>(R.id.txtvwCR)
        val textCL = findViewById<TextView>(R.id.txtvwCL)
        val botonBMas = findViewById<Button>(R.id.bttnBMas)
        val botonEMas = findViewById<Button>(R.id.bttnEMas)
        val botonRMas = findViewById<Button>(R.id.bttnRMas)
        val botonLMas = findViewById<Button>(R.id.bttnLMas)
        val botonBMenos = findViewById<Button>(R.id.bttnBMenos)
        val botonEMenos = findViewById<Button>(R.id.bttnEMenos)
        val botonRMenos = findViewById<Button>(R.id.bttnRMenos)
        val botonLMenos = findViewById<Button>(R.id.bttnLMenos)

        val user = intent.getStringExtra("user")

        botonAñadirFondos.setOnClickListener {
            val intent = Intent(this, Fondos::class.java)
            intent.putExtra("user", user)
            intent.putExtra("fondos", textFondos.text.toString())
            startActivityForResult(intent, 1)
        }
        botonSalir.setOnClickListener {
            finish()
        }

        botonBMas.setOnClickListener {
            comprar(user, "bitcoin", textFondos)
            actualizarCantidad(user, "bitcoin", textCB)
        }
        botonEMas.setOnClickListener {
            comprar(user, "ethereum", textFondos)
            actualizarCantidad(user, "ethereum", textCE)
        }
        botonRMas.setOnClickListener {
            comprar(user, "ripple", textFondos)
            actualizarCantidad(user, "ripple", textCR)
        }
        botonLMas.setOnClickListener {
            comprar(user, "litecoin", textFondos)
            actualizarCantidad(user, "litecoin", textCL)
        }
        botonBMenos.setOnClickListener {
            vender(user, "bitcoin", textFondos)
            actualizarCantidad(user, "bitcoin", textCB)
        }
        botonEMenos.setOnClickListener {
            vender(user, "ethereum", textFondos)
            actualizarCantidad(user, "ethereum", textCE)
        }
        botonRMenos.setOnClickListener {
            vender(user, "ripple", textFondos)
            actualizarCantidad(user, "ripple", textCR)
        }
        botonLMenos.setOnClickListener {
            vender(user, "litecoin", textFondos)
            actualizarCantidad(user, "litecoin", textCL)
        }

        verFondos(user ,textFondos)
        actualizarCantidades(user, textCB, textCE, textCR, textCL)
    }

    @SuppressLint("Range")
    private fun verFondos(usuario: String?, textFondos: TextView) {
        val admin = SQLAd(this, "Cryptos", null, 1)
        val bd = admin.readableDatabase

        try {
            val cursor = bd.query("usuario", arrayOf("saldo"),
                "user = ?", arrayOf(usuario), null, null, null)
            var saldo = 0
            if (cursor.moveToFirst()) {
                saldo = cursor.getInt(cursor.getColumnIndex("saldo"))
            }
            cursor.close()
            bd.close()
            textFondos.text = saldo.toString()
        } catch (e: Exception) {
            bd.close()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val user = intent.getStringExtra("user")
            val textFondos = findViewById<TextView>(R.id.txtvwFondos)
            verFondos(user, textFondos)
        }
    }
    @SuppressLint("Range")
    fun comprar(usuario: String?, moneda: String, textFondos: TextView) {
        val admin = SQLAd(this, "Cryptos", null, 1)
        val bd = admin.readableDatabase
        val cursor = bd.query("usuario", arrayOf(moneda, "saldo"),
            "user = ?", arrayOf(usuario), null, null, null)
        var cantidad = 0
        var saldo = 0
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndex(moneda))
            saldo = cursor.getInt(cursor.getColumnIndex("saldo"))
        }
        cursor.close()
        bd.close()

        val costo: Int = when (moneda) {
            "bitcoin" -> 20000
            "ethereum" -> 5000
            "ripple" -> 700
            "litecoin" -> 20
            else -> 0
        }

        if (saldo >= costo) {
            val admin2 = SQLAd(this, "Cryptos", null, 1)
            val bd2 = admin2.readableDatabase
            val valores = ContentValues()
            valores.put("saldo", saldo - costo)
            bd2.update("usuario", valores, "user = ?", arrayOf(usuario))
            bd2.close()

            val admin3 = SQLAd(this, "Cryptos", null, 1)
            val bd3 = admin3.readableDatabase
            val valores2 = ContentValues()
            valores2.put(moneda, cantidad + 1)
            bd3.update("usuario", valores2, "user = ?", arrayOf(usuario))
            bd3.close()

            verFondos(usuario, textFondos)
        } else {
            Toast.makeText(this, "No tienes suficientes fondos.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun vender(usuario: String?, moneda: String, textFondos: TextView) {
        val admin = SQLAd(this, "Cryptos", null, 1)
        val bd = admin.readableDatabase
        val cursor = bd.query("usuario",
            arrayOf(moneda, "saldo"), "user = ?",
            arrayOf(usuario), null, null, null)
        var cantidad = 0
        var saldo = 0
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndex(moneda))
            saldo = cursor.getInt(cursor.getColumnIndex("saldo"))
        }
        cursor.close()
        bd.close()

        val valorVenta: Int = when (moneda) {
            "bitcoin" -> 20000
            "ethereum" -> 5000
            "ripple" -> 700
            "litecoin" -> 20
            else -> 0
        }

        if (cantidad > 0) {
            val admin2 = SQLAd(this, "Cryptos", null, 1)
            val bd2 = admin2.readableDatabase
            val valores = ContentValues()
            valores.put("saldo", saldo + valorVenta)
            bd2.update("usuario", valores, "user = ?", arrayOf(usuario))
            bd2.close()

            val admin3 = SQLAd(this, "Cryptos", null, 1)
            val bd3 = admin3.readableDatabase
            val valores2 = ContentValues()
            valores2.put(moneda, cantidad - 1)
            bd3.update("usuario", valores2, "user = ?", arrayOf(usuario))
            bd3.close()

            verFondos(usuario, textFondos)
        } else {
            Toast.makeText(this, "No tienes suficientes fondos.", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("Range")
    fun actualizarCantidades(usuario: String?, textCB: TextView, textCE: TextView,
                             textCR: TextView, textCL: TextView) {
        val admin = SQLAd(this, "Cryptos", null, 1)
        val bd = admin.readableDatabase

        try {
            val cursor = bd.query("usuario",
                arrayOf("bitcoin", "ethereum", "ripple", "litecoin"),
                "user = ?", arrayOf(usuario), null, null, null)
            var cantidadBitcoin = 0
            var cantidadEthereum = 0
            var cantidadRipple = 0
            var cantidadLitecoin = 0

            if (cursor.moveToFirst()) {
                cantidadBitcoin = cursor.getInt(cursor.getColumnIndex("bitcoin"))
                cantidadEthereum = cursor.getInt(cursor.getColumnIndex("ethereum"))
                cantidadRipple = cursor.getInt(cursor.getColumnIndex("ripple"))
                cantidadLitecoin = cursor.getInt(cursor.getColumnIndex("litecoin"))
            }

            cursor.close()
            bd.close()

            textCB.text = cantidadBitcoin.toString()
            textCE.text = cantidadEthereum.toString()
            textCR.text = cantidadRipple.toString()
            textCL.text = cantidadLitecoin.toString()
        } catch (e: Exception) {
            bd.close()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("Range")
    fun actualizarCantidad(usuario: String?, moneda: String, textView: TextView) {
        val admin = SQLAd(this, "Cryptos", null, 1)
        val bd = admin.readableDatabase

        try {
            val cursor = bd.query("usuario", arrayOf(moneda), "user = ?", arrayOf(usuario), null, null, null)
            var cantidad = 0

            if (cursor.moveToFirst()) {
                cantidad = cursor.getInt(cursor.getColumnIndex(moneda))
            }

            cursor.close()
            bd.close()

            textView.text = cantidad.toString()
        } catch (e: Exception) {
            bd.close()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
