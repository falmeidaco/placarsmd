package com.example.placarsmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var btnConfiguracoes: Button? = null
    var btnIniciarPartida: Button? = null
    var btnHistorico: Button? = null
    var arquivo: String = "placarsmd"
    var partidaQuantidade: Int = 0
    var proximaPartidaID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializaWidgets()

        btnConfiguracoes?.setOnClickListener {
            val intent = Intent(this@MainActivity, ConfiguracoesActivity::class.java)
            startActivity(intent)
        }

        btnIniciarPartida?.setOnClickListener {
            val intent = Intent(this@MainActivity, PartidaActivity::class.java)
            carregaConfiguracoes(intent)
            startActivity(intent)
        }

        btnHistorico?.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoricoActivity::class.java)
            startActivity(intent)
        }
    }

    fun carregaConfiguracoes(intent:Intent){
        val sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
        if (sharedPreferences !== null) {
            partidaQuantidade = sharedPreferences.getInt("Partidas", 0);
            proximaPartidaID = partidaQuantidade + 1;
            intent.putExtra("Time1", sharedPreferences.getString("_Time1", "Time 1"))
            intent.putExtra("Time2", sharedPreferences.getString("_Time2", "Time 2"))
            intent.putExtra("Tempo", sharedPreferences.getInt("_Tempo", 10))
            intent.putExtra("Partida", proximaPartidaID)
        }
    }

    private fun inicializaWidgets() {
        btnConfiguracoes = findViewById(R.id.btn_configuracoes)
        btnIniciarPartida = findViewById(R.id.btn_iniciar_partida)
        btnHistorico = findViewById(R.id.btn_historico)
    }
}