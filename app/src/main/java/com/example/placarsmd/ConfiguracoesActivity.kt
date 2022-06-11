package com.example.placarsmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ConfiguracoesActivity : AppCompatActivity() {

  private var btnVoltar: Button? = null
  private var btnSalvar: Button? = null
  private var inputTime1: TextView? = null
  private var inputTime2: TextView? = null
  private var inputTempo: TextView? = null
  var partidaQuantidade: Int = 0
  var partidaID: Int = 0
  private var arquivo: String = "placarsmd"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_configuracoes)

    inicializaWidgets()
    carregaConfiguracoes()
    carregaPartidas()

    btnVoltar?.setOnClickListener {
      salvaConfiguracoes()
      finish()
    }

    btnSalvar?.setOnClickListener {
      partidaID++
      partidaQuantidade++
      val intent = Intent(this@ConfiguracoesActivity, PartidaActivity::class.java)
      intent.putExtra("Time1", inputTime1?.text.toString())
      intent.putExtra("Time2", inputTime2?.text.toString())
      intent.putExtra("Tempo", inputTempo?.text.toString().toInt())
      intent.putExtra("Partida", partidaID)
      criaJogo()
      startActivity(intent)
    }

  }

  private fun inicializaWidgets() {
    btnSalvar = findViewById(R.id.btn_salvar_iniciar)
    btnVoltar = findViewById(R.id.btn_voltar_configuracoes)
    inputTime1 = findViewById(R.id.input_time1)
    inputTime2 = findViewById(R.id.input_time2)
    inputTempo = findViewById(R.id.input_tempo)
  }

  private fun carregaConfiguracoes() {
    val sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    if (sharedPreferences !== null) {
      inputTime1?.setText(sharedPreferences.getString("_Time1", "Time 1"))
      inputTime2?.setText(sharedPreferences.getString("_Time2", "Time 2"))
      inputTempo?.setText(sharedPreferences.getInt("_Tempo", 10).toString())
    }
  }

  private fun carregaPartidas(){
    val sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    if(sharedPreferences !== null) {
      partidaQuantidade = sharedPreferences.getInt("Partidas", 0)
      partidaID = partidaQuantidade
    }
  }

  private fun salvaConfiguracoes(){
    val sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    val sharedPreferencesEdit = sharedPreferences.edit()
    sharedPreferencesEdit.putString("_Time1", inputTime1?.text.toString())
    sharedPreferencesEdit.putString("_Time2", inputTime2?.text.toString())
    sharedPreferencesEdit.putInt("_Tempo", inputTempo?.text.toString().toInt())
    sharedPreferencesEdit.commit()
  }

  private fun criaJogo(){
    salvaConfiguracoes()
    val sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    var sharedPreferencesEdit = sharedPreferences.edit()
    sharedPreferencesEdit.putInt("Partidas", partidaQuantidade)
    sharedPreferencesEdit.putInt("Partida_" + partidaID, partidaID)
    sharedPreferencesEdit.putString("Time1_" + partidaID, inputTime1?.text.toString())
    sharedPreferencesEdit.putString("Time2_" + partidaID, inputTime2?.text.toString())
    sharedPreferencesEdit.commit()
  }

}