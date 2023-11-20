package com.example.actividades2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val sharedPreferences=getSharedPreferences("intentos", Context.MODE_PRIVATE)
        var editor=sharedPreferences.edit()
        val tv = findViewById<TextView>(R.id.tv2)
        val bundle = this.intent.extras
        val nombreUsuario = bundle?.getString("Usuario")
        tv.text = "Bienvenido " + nombreUsuario.toString() + "\n" + "Adivina el numero"
        val et=findViewById<EditText>(R.id.etA)
        val bta=findViewById<Button>(R.id.bt2)
        val btv=findViewById<Button>(R.id.bt1)
        val numU=et.text;
        var numA=Random.nextInt(1,21);
        var intentos = 0;

        bta.setOnClickListener {
            if (numU.isEmpty()) {
                Toast.makeText(this, "No has introducido ningun numero.", Toast.LENGTH_LONG).show()
            } else {
                val intento2= Intent(this,Activity3::class.java)
                intento2.putExtra("Usuario",nombreUsuario)
                intento2.putExtra("numRandom",numA)
                intento2.putExtra("numUser",numU.toString().toInt())
                startActivity(intento2)
                if (numU.toString().toInt()!=numA) {
                    intentos++;
                } else {
                    var numero = sharedPreferences.getInt("intentos",0)
                    editor.putString("usuario",nombreUsuario)
                    editor.putInt("intentos",intentos)
                    editor.commit()
                }
                numA=Random.nextInt(1,21);
            }
        }
        btv.setOnClickListener {
            val user = sharedPreferences.getString("usuario","user")
            val intentosF = sharedPreferences.getInt("intentos",0)
            Toast.makeText(this,"El numero de intentos del usuario " + user + " es " + intentosF,Toast.LENGTH_LONG).show()
            finish()
        }
    }
}