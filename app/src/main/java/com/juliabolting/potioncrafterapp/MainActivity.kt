package com.juliabolting.potioncrafterapp

import RecipeBookScreen
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juliabolting.potioncrafterapp.ui.screen.InventoryScreen
import com.juliabolting.potioncrafterapp.ui.screen.PotionCraftActivity
import com.juliabolting.potioncrafterapp.ui.theme.PotionCrafterAppTheme

/**
 * Activity principal do aplicativo PotionCrafter.
 *
 * Exibe a tela principal com opções para navegar entre as funcionalidades:
 * criar poção, visualizar inventário e consultar o grimório de receitas.
 *
 * Recebe o nome do jogador via Intent, exibindo uma saudação personalizada.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtém o nome do jogador enviado pela activity anterior, ou usa padrão
        val playerName = intent.getStringExtra("player_name") ?: getString(R.string.aventureiro)
        Log.d("PotionCrafter", "playerName: ${playerName}")

        enableEdgeToEdge()

        setContent {
            PotionCrafterAppTheme {
                // Estado para controlar qual tela está ativa ("main", "inventory", "recipebook")
                val currentScreen = remember { mutableStateOf("main") }

                Surface(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen.value) {
                        "main" -> MainScreen(
                            playerName = playerName,
                            onGoToInventory = { currentScreen.value = "inventory" },
                            onGoToPotionCraft = {
                                startActivity(Intent(this, PotionCraftActivity::class.java))
                            },
                            onGoToRecipeBook = { currentScreen.value = "recipebook" }
                        )
                        "inventory" -> InventoryScreen()
                        "recipebook" -> RecipeBookScreen()
                    }
                }
            }
        }
    }
}

/**
 * Tela principal da aplicação com botões para navegar entre funcionalidades.
 *
 * @param playerName Nome do jogador exibido na saudação
 * @param onGoToInventory Callback para navegar para a tela de inventário
 * @param onGoToPotionCraft Callback para iniciar a criação de uma nova poção
 * @param onGoToRecipeBook Callback para abrir o grimório de receitas
 */
@Composable
fun MainScreen(
    playerName: String,
    onGoToInventory: () -> Unit,
    onGoToPotionCraft: () -> Unit,
    onGoToRecipeBook: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagem de fundo da tela principal
        Image(
            painter = painterResource(id = R.drawable.fundo_potion),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bem_vindo_mestre, playerName),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(
                text = stringResource(R.string.o_que_deseja_fazer_na_sua_jornada_alqu_mica),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { onGoToPotionCraft() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA))
            ) {
                Text(stringResource(R.string.criar_nova_po_o))
            }

            Button(
                onClick = { onGoToInventory() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
            ) {
                Text(stringResource(R.string.ver_ingredientes))
            }

            Button(
                onClick = { onGoToRecipeBook() },
                modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Text(stringResource(R.string.consultar_grim_rio_de_receitas))
            }
        }
    }
}