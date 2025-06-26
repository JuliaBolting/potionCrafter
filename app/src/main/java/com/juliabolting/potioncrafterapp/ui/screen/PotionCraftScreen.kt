package com.juliabolting.potioncrafterapp.ui.screen

import android.content.Context
import android.content.Intent
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
import com.juliabolting.potioncrafterapp.data.model.Potion
import com.juliabolting.potioncrafterapp.data.model.Recipe
import kotlinx.coroutines.launch

/**
 * Composable que exibe a tela para criação de poções com seleção de ingredientes,
 * incluindo animações de nível e experiência, e um botão para criar a poção.
 *
 * @param onGoBack Callback para voltar à tela principal.
 */
@Composable
fun PotionCraftAppScreen(onGoBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val ingredientDao = db.ingredientDao()
    val recipeDao = db.recipeDao()
    val crossRefDao = db.ingredientRecipeDao()
    val coroutineScope = rememberCoroutineScope()

    // Recuperar player_id do SharedPreferences
    val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val savedPlayerId = sharedPref.getInt("player_id", -1)

    // Estado dos ingredientes disponíveis no banco
    var allIngredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }
    // Estado dos ingredientes selecionados pelo usuário
    var selectedIngredients by remember { mutableStateOf(setOf<Ingredient>()) }
    // Estado para controle do diálogo de resultado
    var showResultDialog by remember { mutableStateOf(false) }

    // Carrega os ingredientes do banco ao iniciar o Composable
    LaunchedEffect(Unit) {
        allIngredients = ingredientDao.getAllIngredients()
    }

    // Lógica fictícia para obter XP e Level (substitua pela sua implementação real)
    val xp = remember { mutableStateOf(100) } // Exemplo, ajuste com base no player_id
    val level = remember { mutableStateOf(5) } // Exemplo, ajuste com base no player_id
    // Exemplo: Se você tem uma função para buscar XP/Level no banco ou SharedPreferences
    // LaunchedEffect(savedPlayerId) { xp.value = getXPFromDatabase(savedPlayerId) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Título com animações
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp), // Reduzido o padding superior
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

                // Animação de estrelas (XP) com texto ao lado dentro de uma caixinha amarela clarinha
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
                        val starComposition by rememberLottieComposition(LottieCompositionSpec.Asset("starGIF.json"))
                        LottieAnimation(
                            composition = starComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "XP ${xp.value}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                // Animação de pódio (Level) com texto ao lado dentro de uma caixinha azul bebê
                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp), // Mais redondo
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Azul bebê
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Reduzido
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(2.dp) // Mais rente ao texto
                    ) {
                        val podiumComposition by rememberLottieComposition(LottieCompositionSpec.Asset("podiumGIF.json"))
                        LottieAnimation(
                            composition = podiumComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Level ${level.value}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface, // Cor de fundo da tela
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Label
            Text(
                text = stringResource(R.string.escolha_at_3_ingredientes),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de ingredientes
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
                                context.resources.getIdentifier(ingredient.imagem, "drawable", context.packageName)
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
                            Column (
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = ingredient.nome,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                // Raridade em letras pequenas com cor específica
                                Text(
                                    text = ingredient.raridade ?: "Comum", // Substitua 'raridade' pelo campo correto
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                    color = when (ingredient.raridade?.lowercase()) {
                                        "raro" -> Color(0xFF0000FF) // Azul
                                        "épico" -> Color(0xFF800080) // Roxo
                                        "lendário" -> Color(0xFFFFA500) // Laranja
                                        "comum" -> Color(0xFF808080) // Cinza
                                        "incomum" -> Color(0xFF32CD32) // Verde
                                        else -> Color(0xFF808080) // Padrão: Cinza
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

            // Botão Criar Poção e Botão de Voltar em uma Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
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

                            showResultDialog = true
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

        // Diálogo de resultado
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
                        val cauldronComposition by rememberLottieComposition(LottieCompositionSpec.Asset("cauldronGIF.json"))
                        LottieAnimation(
                            composition = cauldronComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(80.dp) // Ajuste o tamanho conforme necessário
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