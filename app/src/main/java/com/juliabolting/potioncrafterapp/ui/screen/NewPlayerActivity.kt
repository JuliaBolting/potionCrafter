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
import android.view.animation.LinearInterpolator
import android.widget.TextView

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

        // Animação de rotação infinita no texto animado
        val rotation = ObjectAnimator.ofFloat(animText, "rotation", 0f, 360f).apply {
            duration = 4000 // duração 4 segundos
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        // Clique do botão para criar novo jogador
        btnStart.setOnClickListener {
            val name = editName.text.toString()
            if (name.isNotBlank()) {
                val newPlayer = Player(name = name, level = 1, xp = 0) // não define o ID
                lifecycleScope.launch {
                    val playerId = playerDao.insert(newPlayer) // retorna o ID gerado

                    // Salva o ID no SharedPreferences
                    val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putInt("player_id", playerId.toInt()).apply()

                    // Vai para a MainActivity
                    val intent = Intent(this@NewPlayerActivity, MainActivity::class.java)
                    intent.putExtra("playerName", name)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, getString(R.string.digite_um_nome), Toast.LENGTH_SHORT).show()
            }
        }
    }
}