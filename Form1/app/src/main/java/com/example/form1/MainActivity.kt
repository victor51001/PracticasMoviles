package com.example.form1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val numero1 = findViewById<EditText>(R.id.txtN1);
        val numero2 = findViewById<EditText>(R.id.txtN2);
        var operacion = findViewById<EditText>(R.id.txtOp)
        val resultado = findViewById<Button>(R.id.bttn);
        val tv = findViewById<TextView>(R.id.txtV);

        resultado.setOnClickListener {

            val n1 = numero1.text.toString().toInt();
            val n2 = numero2.text.toString().toInt();
            val op = operacion.text.toString();
            var resul = 0;

            when (op) {
                "-" -> {
                    resul = n1 - n2
                    Toast.makeText(this,"LA RESTA ES " + resul, Toast.LENGTH_LONG).show()
                    tv.text="El resultado de la resta es ${resul.toString()}"
                }
                "+" -> {
                    resul = n1 + n2
                    Toast.makeText(this,"LA SUMA ES " + resul, Toast.LENGTH_LONG).show()
                    tv.text="El resultado de la suma es ${resul.toString()}"
                }
            }
        }
    }
}