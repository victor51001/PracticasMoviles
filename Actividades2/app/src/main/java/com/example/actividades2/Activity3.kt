package com.example.actividades2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val tv = findViewById<TextView>(R.id.tv3)
        val btv = findViewById<Button>(R.id.btV)
        val bundle = this.intent.extras
        val user = bundle?.getString("Usuario")
        val numA = bundle?.getInt("numRandom")
        val numU = bundle?.getInt("numUser")

        if (numA==numU)  {
            tv.text = "Enhorabuena " + user.toString() + " has acertado el numero" +
                "\n" + " El numero era " + numA.toString()
        } else {
            tv.text = user.toString() + " no has acertado el numero" +
                    "\n" + " El numero era " + numA.toString() +
                "\n" + "Tu numero era " + numU.toString()
        }
        btv.setOnClickListener {
            finish()
        }
    }
}