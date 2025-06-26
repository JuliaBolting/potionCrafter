package com.juliabolting.potioncrafterapp.ui.screenimport

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.Ingredient
import com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef
import com.juliabolting.potioncrafterapp.data.model.Player
import com.juliabolting.potioncrafterapp.data.model.Recipe
import kotlinx.coroutines.launch

/**Composable que exibe a tela para criação de poções com seleção de ingredientes,
incluindo animações de nível e experiência, e um botão para criar a poção.

@param onGoBack Callback para voltar à tela principal.
 */
@Composable
fun PotionCraftAppScreen(onGoBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val ingredientDao = db.ingredientDao()
    val recipeDao = db.recipeDao()
    val crossRefDao = db.ingredientRecipeDao()
    val playerDao = db.playerDao()
    val coroutineScope = rememberCoroutineScope()
    val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val savedPlayerId =
        sharedPref.getInt("player_id", -1)
    var player by remember { mutableStateOf<Player?>(null) }
    var xp by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(1) }
    LaunchedEffect(savedPlayerId) {
        if (savedPlayerId != -1) {
            try {
                player = playerDao.getPlayerById(savedPlayerId)
                Log.d("PotionCraftAppScreen", "Player recuperado: $player")
                if (player != null) {
                    xp = player!!.xp
                    level = player!!.level
                } else {
                    Log.w(
                        "PotionCraftAppScreen",
                        "Nenhum player encontrado para ID: $savedPlayerId"
                    )
                }
            } catch (e: Exception) {
                Log.e("PotionCraftAppScreen", "Erro ao buscar player: ${e.message}")
            }
        } else {
            Log.w("PotionCraftAppScreen", "savedPlayerId é -1, nenhum player carregado")
            Toast.makeText(context, "player vazio", Toast.LENGTH_LONG).show()
        }
    }
    var allIngredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }
    var selectedIngredients by remember { mutableStateOf(setOf<Ingredient>()) }
    var showResultDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        allIngredients = ingredientDao.getAllIngredients()
    }
    suspend fun updatePlayer(newXp: Int, newLevel: Int) {
        player?.let { currentPlayer ->
            val updatedPlayer = currentPlayer.copy(xp = newXp, level = newLevel)
            playerDao.update(updatedPlayer)
            player = updatedPlayer
            xp = newXp
            level = newLevel
            Log.d(
                "PotionCraftAppScreen",
                context.getString(R.string.jogador_atualizado_xp_level, newXp, newLevel)
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Poções",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Card(
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        val starComposition by rememberLottieComposition(
                            LottieCompositionSpec.Asset(
                                "starGIF.json"
                            )
                        )
                        LottieAnimation(
                            composition = starComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "XP $xp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        val podiumComposition by rememberLottieComposition(
                            LottieCompositionSpec.Asset(
                                "trophyGIF.json"
                            )
                        )
                        LottieAnimation(
                            composition = podiumComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Level $level",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.escolha_at_3_ingredientes),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(allIngredients) { ingredient ->
                    val isSelected = ingredient in selectedIngredients
                    val canSelect = selectedIngredients.size < 3 || isSelected

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = isSelected,
                                enabled = canSelect,
                                onValueChange = {
                                    selectedIngredients = if (isSelected) {
                                        selectedIngredients - ingredient
                                    } else {
                                        selectedIngredients + ingredient
                                    }
                                }
                            ),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFF4CAF50) else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val context = LocalContext.current
                            val imageId = remember(ingredient.imagem) {
                                context.resources.getIdentifier(
                                    ingredient.imagem,
                                    "drawable",
                                    context.packageName
                                )
                            }

                            if (imageId != 0) {
                                val painter = painterResource(id = imageId)

                                Image(
                                    painter = painter,
                                    contentDescription = stringResource(R.string.imagem_do_ingrediente),
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = ingredient.nome,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = ingredient.raridade,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                    color = when (ingredient.raridade.lowercase()) {
                                        stringResource(R.string.raro).lowercase() -> Color(
                                            0xFF0000FF
                                        )

                                        stringResource(R.string.pico).lowercase() -> Color(
                                            0xFF800080
                                        )

                                        stringResource(R.string.lend_rio).lowercase() -> Color(
                                            0xFFFFA500
                                        )

                                        stringResource(R.string.comum).lowercase() -> Color(
                                            0xFF808080
                                        )

                                        stringResource(R.string.incomum).lowercase() -> Color(
                                            0xFF32CD32
                                        )

                                        else -> Color(0xFF808080)
                                    }
                                )
                            }
                            // Caixa de seleção visual dentro do Card
                            Box(
                                modifier = Modifier.size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Canvas(modifier = Modifier.size(16.dp), onDraw = {
                                        drawCircle(
                                            color = Color.White,
                                            radius = size.minDimension / 2
                                        )
                                    })
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (selectedIngredients.isNotEmpty() && player != null) {
                                val ingredientes = selectedIngredients.toList()
                                val nomeReceita = ingredientes.joinToString(" + ") { it.nome }

                                val recipe = Recipe(
                                    nome = nomeReceita,
                                    descricao = context.getString(
                                        R.string.po_o_criada_com_ingredientes,
                                        ingredientes.size
                                    ),
                                    resultado = nomeReceita
                                )
                                val recipeIdLong = recipeDao.insert(recipe).toString()
                                val recipeId = recipeIdLong.toInt()

                                ingredientes.forEach {
                                    crossRefDao.insert(
                                        IngredienteReceitaCrossRef(
                                            receitaId = recipeId,
                                            ingredienteId = it.id
                                        )
                                    )
                                }

                                var newXp = xp + 5
                                var newLevel = level
                                if (newXp >= 10) {
                                    newXp = 0
                                    newLevel += 1
                                    Toast.makeText(
                                        context,
                                        context.getString(
                                            R.string.parab_ns_subiu_para_level,
                                            newLevel
                                        ),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                updatePlayer(newXp, newLevel)

                                showResultDialog = true
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.selecione_ingredientes_ou_crie_um_jogador),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    enabled = selectedIngredients.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(stringResource(R.string.criar_po_o))
                }

                Button(
                    onClick = onGoBack,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }

        if (showResultDialog) {
            AlertDialog(
                onDismissRequest = { showResultDialog = false },
                confirmButton = {
                    TextButton(onClick = { showResultDialog = false }) {
                        Text(stringResource(R.string.fechar))
                    }
                },
                title = { Text(stringResource(R.string.po_o_criada)) },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val cauldronComposition by rememberLottieComposition(
                            LottieCompositionSpec.Asset(
                                "cauldronGIF.json"
                            )
                        )
                        LottieAnimation(
                            composition = cauldronComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 16.dp)
                        )
                        Text(
                            stringResource(
                                R.string.sua_po_o_cont_m,
                                selectedIngredients.joinToString(", ") { it.nome }
                            )
                        )
                    }
                }
            )
        }
    }
}