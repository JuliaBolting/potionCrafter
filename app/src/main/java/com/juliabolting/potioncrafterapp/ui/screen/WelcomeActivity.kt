package com.juliabolting.potioncrafterapp.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.juliabolting.potioncrafterapp.MainActivity
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.dao.IngredientDAO
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.Player
import kotlinx.coroutines.launch

/**
 * Activity de boas-vindas do aplicativo.
 *
 * Permite ao usuário criar um novo jogador ou continuar com um jogador já salvo.
 * Caso não haja jogador salvo, exibe mensagem ao tentar continuar.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var playerDao: PlayerDAO
    private lateinit var ingredientDao: IngredientDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Instancia do banco de dados e DAOs
        db = AppDatabase.getInstance(this)
        playerDao = db.playerDao()
        ingredientDao = db.ingredientDao()

        val btnNewPlayer = findViewById<Button>(R.id.btnNewPlayer)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        var clickCount = 0
        val titleText = findViewById<TextView>(R.id.txtTitleWelcome)

        fun mostrarErro() {
            Toast.makeText(
                this@WelcomeActivity,
                getString(R.string.nenhum_jogador_salvo),
                Toast.LENGTH_SHORT
            ).show()
        }


        titleText.setOnClickListener {
            clickCount++
            if (clickCount == 7) {
                // Apaga o banco de dados Room
                deleteDatabase("potioncrafter.db") // substitua pelo nome real do seu banco

                Toast.makeText(this, "Banco de dados resetado!", Toast.LENGTH_SHORT).show()

                // Reinicia a activity para refletir as mudanças
                val intent = Intent(this, WelcomeActivity::class.java)
                finish()
                startActivity(intent)
            }
        }

        // Navega para tela de criação de novo jogador
        btnNewPlayer.setOnClickListener {
            startActivity(Intent(this, NewPlayerActivity::class.java))
        }

        // Continua com o jogador salvo com id 1, se existir
        btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                val savedPlayerId = sharedPref.getInt("player_id", -1)
                var player: Player? = null

                Log.d("PotionCrafter", "savedPlayerId: ${savedPlayerId}")

                if (savedPlayerId != -1) {
                    player = playerDao.getPlayerById(savedPlayerId)
                    Log.d("PotionCrafter", "player: ${player?.name}")
                    if (player != null) {
                        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                        intent.putExtra("playerName", player.name)
                        startActivity(intent)
                    } else {
                        mostrarErro()
                    }
                } else {
                    mostrarErro()
                }

                if (player != null) {
                    val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                    intent.putExtra("playerName", player.name)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@WelcomeActivity,
                        getString(R.string.nenhum_jogador_salvo),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}