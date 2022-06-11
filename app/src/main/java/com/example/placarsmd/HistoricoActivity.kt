package com.example.placarsmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HistoricoActivity : AppCompatActivity() {
  private var arquivo: String = "placarsmd"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_historico)
    exibitHistorico()

    val btnLimpar = findViewById<Button>(R.id.btn_limpar_historico)
    val btnVoltar = findViewById<Button>(R.id.btn_voltar_historico)

    btnLimpar.setOnClickListener {
      val sharedPreferences : SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
      if(sharedPreferences!=null){
        sharedPreferences.edit().clear().commit()
      }
      exibitHistorico()
    }

    btnVoltar.setOnClickListener {
      val intent = Intent(this@HistoricoActivity, MainActivity::class.java)
      startActivity(intent)
    }

  }

  private fun exibitHistorico(){
    var sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    var textPartidas = findViewById<TextView>(R.id.text_historico)
    var numPartidas: Int = 0
    if(sharedPreferences!==null){
      val numPartidas = sharedPreferences.getInt("Partidas", 0);
      if (numPartidas != 0) {
        for (i in 0..numPartidas){
          if(i > 0) {
            textPartidas.text = textPartidas.text.toString() + sharedPreferences.getString("Time1_" + i, "") + " (" + sharedPreferences.getInt("PontosTime1_" + i, 0) + ") x (" + sharedPreferences.getInt("PontosTime2_" + i, 0) + ") " + sharedPreferences.getString("Time2_" + i, "") + "\n"
          }
        }
      } else {
        textPartidas.text = "Nenhuma partida registrada.";
      }
    } else {
      textPartidas.text = "Nenhuma partida registrada.";
    }
  }

}