package com.example.jogoadivinhacao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val rgMaximo = findViewById<RadioGroup>(R.id.rgMaximo)
        val btnAbrir = findViewById<Button>(R.id.btnAbrir)

        btnAbrir.setOnClickListener {
            val nome = edtNome.text.toString().trim()

            // Validação: nome não pode ficar vazio
            if (nome.isEmpty()) {
                edtNome.error = getString(R.string.erro_nome)
                return@setOnClickListener
            }

            // Limite escolhido no RadioGroup (10, 50 ou 100)
            val maximo = when (rgMaximo.checkedRadioButtonId) {
                R.id.rb10 -> 10
                R.id.rb100 -> 100
                else -> 50
            }

            // O Intent leva a "bagagem": nome e maximo
            val intent = Intent(this, JogoActivity::class.java)
            intent.putExtra("nome", nome)
            intent.putExtra("maximo", maximo)
            startActivity(intent)
        }
    }
}
