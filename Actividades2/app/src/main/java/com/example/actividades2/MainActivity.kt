package com.example.actividades2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val et=findViewById<TextView>(R.id.etU)
        val bt=findViewById<Button>(R.id.bt)
        bt.setOnClickListener {
            val intento= Intent(this,Activity2::class.java)
            intento.putExtra("Usuario",et.text.toString())
            startActivity(intento)
        }
    }
}