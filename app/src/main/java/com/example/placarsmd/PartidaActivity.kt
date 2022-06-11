package com.example.placarsmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Button
import android.widget.TextView

class PartidaActivity : AppCompatActivity() {

  var btnTime1: Button? = null
  var btnTime2: Button? = null
  var labelTime1: TextView? = null
  var labelTime2: TextView? = null

  var pontosTime1: Int = 0
  var pontosTime2: Int = 0
  val pontosHistorico = ArrayList<Int>(50)

  var btnDesfazer: Button? = null
  var btnTimer: Button? = null
  var btnFinalizar: Button? = null

  var partidaID: Int = 0

  var partidaEmAndamento = false
  var tempoPartida: Int = 0
  var tempoPausa: Long = 0

  var arquivo: String = "placarsmd"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_partida)

    var cronometro: Chronometer? = findViewById<Chronometer>(R.id.label_cronometro);

    inicializaWidgets()
    carregaConfiguracoes(intent)

    btnTime1?.setOnClickListener {
      pontosTime1 += 1
      pontosHistorico.add(1)
      btnTime1?.text = pontosTime1.toString()
    }

    btnTime2?.setOnClickListener {
      pontosTime2 += 1
      pontosHistorico.add(2)
      btnTime2?.text = pontosTime2.toString()
    }

    btnDesfazer?.setOnClickListener {
      if(pontosHistorico.get(pontosHistorico.lastIndex) == 1){
        pontosTime1 -= 1
        btnTime1?.text = pontosTime1.toString()
      } else {
        pontosTime2 -= 1
        btnTime2?.text = pontosTime2.toString()
      }
      pontosHistorico.removeAt(pontosHistorico.lastIndex)
    }

    btnTimer?.setOnClickListener {
      if (!partidaEmAndamento) {
        if (tempoPausa != 0.toLong()) {
          cronometro?.setBase(cronometro?.getBase() + SystemClock.elapsedRealtime() - tempoPausa)
        } else {
          cronometro?.setBase(SystemClock.elapsedRealtime())
        }
        btnTimer?.text = "Pausar"
        cronometro?.start()
        partidaEmAndamento = true
      } else {
        tempoPausa = SystemClock.elapsedRealtime()
        cronometro?.stop()
        partidaEmAndamento = false
        btnTimer?.text = "Continuar"
      }
    }

    btnFinalizar?.setOnClickListener {
      val cronometro: Chronometer? = findViewById<Chronometer>(R.id.label_cronometro)
      cronometro?.stop()
      salvarJogo()
      val intent = Intent(this@PartidaActivity, MainActivity::class.java)
      startActivity(intent)
    }

    cronometro?.setOnChronometerTickListener {
      if(SystemClock.elapsedRealtime() - cronometro.getBase() > tempoPartida * 60000 ){
        tempoPausa = 0
        cronometro.stop();
        partidaEmAndamento = false;
        salvarJogo()
        val intent = Intent(this@PartidaActivity, MainActivity::class.java)
        startActivity(intent)
      }
    }

  }

  private fun inicializaWidgets() {
    btnTime1 = findViewById(R.id.btn_time1)
    btnTime2 = findViewById(R.id.btn_time2)
    btnTimer = findViewById(R.id.btn_timer)
    labelTime1 = findViewById(R.id.label_time1)
    labelTime2 = findViewById(R.id.label_time2)
    btnDesfazer = findViewById(R.id.btn_desfazer)
    btnFinalizar = findViewById(R.id.btn_finalizar)
  }

  fun carregaConfiguracoes(intent: Intent?) {
    val tempo = intent?.getIntExtra("Tempo", 0)
    val cronometro: Chronometer? = findViewById<Chronometer>(R.id.label_cronometro)
    if (tempo != null) {
      tempoPartida = tempo
      cronometro?.setBase(SystemClock.elapsedRealtime() + tempo.toLong())
    }
    val id  = intent?.getIntExtra("Partida", 0)
    if (id != null) {
      partidaID = id
    }
    labelTime1?.text = intent?.getStringExtra("Time1")
    labelTime2?.text = intent?.getStringExtra("Time2")
  }

  fun salvarJogo(){
    var sharedPreferences: SharedPreferences = getSharedPreferences(arquivo, Context.MODE_PRIVATE)
    var sharedPreferencesEditor = sharedPreferences.edit()
    sharedPreferencesEditor.putString("Time1_"+partidaID, labelTime1?.text.toString())
    sharedPreferencesEditor.putString("Time2_"+partidaID, labelTime2?.text.toString())
    sharedPreferencesEditor.putInt("PontosTime1_"+partidaID, btnTime1?.text.toString().toInt())
    sharedPreferencesEditor.putInt("PontosTime2_"+partidaID, btnTime2?.text.toString().toInt())
    sharedPreferencesEditor.putInt("Partida_"+partidaID, partidaID)
    sharedPreferencesEditor.putInt("Partidas", partidaID)
    sharedPreferencesEditor.apply()
  }

}