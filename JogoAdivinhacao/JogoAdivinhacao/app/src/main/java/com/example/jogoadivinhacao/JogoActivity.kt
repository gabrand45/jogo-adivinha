package com.example.jogoadivinhacao

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.random.Random

class JogoActivity : AppCompatActivity() {

    private var secreto = 0
    private var maximo = 50
    private var tentativas = 0
    private var nome = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        // Recebendo os dados enviados pela MainActivity
        nome = intent.getStringExtra("nome") ?: "Jogador"
        maximo = intent.getIntExtra("maximo", 50)

        // O número secreto é sorteado aqui, não na primeira tela
        secreto = Random.nextInt(1, maximo + 1)

        val txtBoasVindas = findViewById<TextView>(R.id.txtBoasVindas)
        val edtPalpite = findViewById<EditText>(R.id.edtPalpite)
        val btnChutar = findViewById<Button>(R.id.btnChutar)
        val txtDica = findViewById<TextView>(R.id.txtDica)
        val txtTentativas = findViewById<TextView>(R.id.txtTentativas)
        val btnJogarDeNovo = findViewById<Button>(R.id.btnJogarDeNovo)

        txtBoasVindas.text = getString(R.string.boas_vindas, nome, maximo)
        txtTentativas.text = getString(R.string.tentativas, tentativas)

        btnChutar.setOnClickListener {
            // Validação do palpite antes de usar o número
            val palpite = edtPalpite.text.toString().toIntOrNull()
            if (palpite == null || palpite < 1 || palpite > maximo) {
                edtPalpite.error = getString(R.string.erro_palpite, maximo)
                return@setOnClickListener
            }

            tentativas++
            txtTentativas.text = getString(R.string.tentativas, tentativas)
            txtDica.visibility = View.VISIBLE

            if (palpite == secreto) {
                // Desafio: mensagem final + botão "Jogar de novo"
                txtDica.text = if (tentativas == 1) {
                    getString(R.string.acertou_uma, nome)
                } else {
                    getString(R.string.acertou, nome, tentativas)
                }
                btnChutar.isEnabled = false
                edtPalpite.isEnabled = false
                btnJogarDeNovo.visibility = View.VISIBLE
            } else {
                val diferenca = abs(palpite - secreto)
                val quente = diferenca <= maximo / 10   // 10% do limite
                val maior = palpite < secreto           // o segredo é maior que o palpite

                txtDica.text = getString(
                    when {
                        quente && maior -> R.string.dica_quente_maior
                        quente -> R.string.dica_quente_menor
                        maior -> R.string.dica_frio_maior
                        else -> R.string.dica_frio_menor
                    }
                )
                edtPalpite.text.clear()
            }
        }

        // finish() encerra esta tela e volta para a MainActivity que está embaixo na pilha
        btnJogarDeNovo.setOnClickListener {
            finish()
        }
    }
}
