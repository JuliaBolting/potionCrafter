package com.juliabolting.potioncrafterapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.juliabolting.potioncrafterapp.ui.screen.InventoryScreen
import com.juliabolting.potioncrafterapp.ui.screen.RecipeBookScreen
import com.juliabolting.potioncrafterapp.ui.screenimport.PotionCraftAppScreen
import com.juliabolting.potioncrafterapp.ui.theme.PotionCrafterAppTheme

val MedievalFont = FontFamily(Font(R.font.medieval_sharp))

/**
 * Activity principal do aplicativo PotionCrafter.
 *
 * Exibe a tela principal com opções para navegar entre as funcionalidades:
 * criar poção, visualizar inventário e consultar o grimório de receitas.
 *
 * Recebe o nome do jogador via Intent, exibindo uma saudação personalizada.
 */
class MainActivity : ComponentActivity() {
    private val snackbarHostState = SnackbarHostState()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerName = intent.getStringExtra("player_name") ?: getString(R.string.aventureiro)
        Log.d("PotionCrafter", "playerName: ${playerName}")

        enableEdgeToEdge()

        setContent {
            PotionCrafterAppTheme {
                val currentScreen = remember { mutableStateOf("main") }

                Surface(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen.value) {
                        "main" -> MainScreen(
                            playerName = playerName,
                            onGoToInventory = { currentScreen.value = "inventory" },
                            onGoToPotionCraft = {
                                currentScreen.value = "potion"
                            },
                            onGoToRecipeBook = { currentScreen.value = "recipebook" }
                        )

                        "inventory" -> InventoryScreen(
                            onGoBack = { currentScreen.value = "main" }
                        )

                        "recipebook" -> RecipeBookScreen(
                            onGoBack = { currentScreen.value = "main" },
                            snackbarHostState = snackbarHostState
                        )

                        "potion" -> PotionCraftAppScreen(
                            onGoBack = { currentScreen.value = "main" }

                        )
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
    onGoToRecipeBook: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bem_vindo_mestre, playerName),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = MedievalFont,
                    fontSize = 28.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(R.string.o_que_deseja_fazer_na_sua_jornada_alqu_mica),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = MedievalFont,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("potionGIF.json"))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(300.dp) // Ajuste o tamanho conforme necessário
                .padding(vertical = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Criar Poção",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = MedievalFont,
                    fontSize = 16.sp,
                    color = Color(0xFF8E24AA)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Button(
                onClick = { onGoToPotionCraft() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA))
            ) {
                Text(stringResource(R.string.criar_nova_po_o))
            }

            Text(
                text = "Inventário",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = MedievalFont,
                    fontSize = 16.sp,
                    color = Color(0xFF43A047)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Button(
                onClick = { onGoToInventory() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
            ) {
                Text(stringResource(R.string.ver_ingredientes))
            }

            Text(
                text = "Grimório",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = MedievalFont,
                    fontSize = 16.sp,
                    color = Color(0xFF1E88E5)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
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