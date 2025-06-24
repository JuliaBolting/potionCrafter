package com.juliabolting.potioncrafterapp.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.juliabolting.potioncrafterapp.MainActivity
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.dao.IngredientDAO
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
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

        // Navega para tela de criação de novo jogador
        btnNewPlayer.setOnClickListener {
            startActivity(Intent(this, NewPlayerActivity::class.java))
        }

        // Continua com o jogador salvo com id 1, se existir
        btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val player = playerDao.getPlayerById(1)
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