package com.juliabolting.potioncrafterapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.juliabolting.potioncrafterapp.MainActivity
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.Player
import kotlinx.coroutines.launch
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.TextView
import kotlinx.coroutines.delay

/**
 * Activity para criar um novo jogador.
 *
 * Exibe uma tela com um campo para o usuário digitar seu nome,
 * e botão para iniciar o jogo com o novo jogador salvo no banco.
 */
class NewPlayerActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var playerDao: PlayerDAO

    /**
     * Inicializa a activity, configura os componentes da UI,
     * animação do texto e o clique do botão para salvar jogador.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_player)

        db = AppDatabase.getInstance(this)
        playerDao = db.playerDao()

        val editName = findViewById<EditText>(R.id.editPlayerName)
        val btnStart = findViewById<Button>(R.id.btnCreatePlayer)
        val animText = findViewById<TextView>(R.id.textAnimInter)

        ObjectAnimator.ofFloat(animText, "rotation", 0f, 360f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        btnStart.setOnClickListener {
            val name = editName.text.toString()
            if (name.isNotBlank()) {
                val newPlayer = Player(name = name, level = 1, xp = 0)
                lifecycleScope.launch {
                    try {
                        val playerId = playerDao.insert(newPlayer)
                        Log.d("NewPlayerActivity", "Jogador inserido com ID: $playerId")

                        val insertedPlayer = playerDao.getPlayerById(playerId.toInt())
                        if (insertedPlayer != null) {
                            Log.d("NewPlayerActivity", "Jogador verificado: ${insertedPlayer.name}")
                        } else {
                            Log.e("NewPlayerActivity", "Jogador não encontrado após inserção com ID: $playerId")
                        }

                        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putInt("player_id", playerId.toInt())
                            apply()
                        }
                        Log.d("NewPlayerActivity", "player_id salvo: ${playerId.toInt()}")

                        delay(500)
                        val intent = Intent(this@NewPlayerActivity, MainActivity::class.java)
                        intent.putExtra("player_name", name)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        Log.e("NewPlayerActivity", "Erro ao inserir jogador: ${e.message}")
                        Toast.makeText(this@NewPlayerActivity, "Erro ao criar jogador", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.digite_um_nome), Toast.LENGTH_SHORT).show()
            }
        }
    }
}