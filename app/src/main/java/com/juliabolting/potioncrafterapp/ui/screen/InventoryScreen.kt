package com.juliabolting.potioncrafterapp.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import coil.compose.rememberAsyncImagePainter
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.Ingredient

/**
 * Tela que exibe a lista de ingredientes disponíveis no inventário.
 *
 * Faz a consulta dos ingredientes no banco de dados e exibe cada um em um cartão.
 */
@Composable
fun InventoryScreen(onGoBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val ingredientDao = db.ingredientDao()

    var ingredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }

    LaunchedEffect(Unit) {
        ingredients = ingredientDao.getAllIngredients()
        Log.d("PotionCrafter", "Ingredientes carregados: ${ingredients.size}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.invent_rio_de_ingredientes),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
            )

            if (ingredients.isEmpty()) {
                Text(
                    stringResource(R.string.text_nada_ingrediente),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(ingredients.size) { index ->
                        val ingredient = ingredients[index]
                        IngredientCard(ingredient)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { onGoBack() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 45.dp)
                .size(48.dp),
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }
    }
}

/**
 * Composable que exibe um cartão com as informações do ingrediente.
 *
 * A cor do fundo e o emoji variam conforme a raridade do ingrediente.
 *
 * @param ingredient O ingrediente que será exibido.
 */
@Composable
fun IngredientCard(ingredient: Ingredient) {
    val backgroundColor = when (ingredient.raridade.lowercase()) {
        stringResource(R.string.comum) -> Color(0xFFEEEEEE)
        stringResource(R.string.incomum) -> Color(0xFFBBDEFB)
        stringResource(R.string.raro) -> Color(0xFFD1C4E9)
        stringResource(R.string.lend_rio) -> Color(0xFFFFF59D)
        stringResource(R.string.pico) -> Color(0xFF93DBFF)
        else -> MaterialTheme.colorScheme.surface
    }

    val emoji = when (ingredient.raridade.lowercase()) {
        stringResource(R.string.comum) -> "🌱"
        stringResource(R.string.incomum) -> "🍄"
        stringResource(R.string.raro) -> "💎"
        stringResource(R.string.lend_rio) -> "🐉"
        else -> "🔸"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
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
                        .size(64.dp)
                        .padding(end = 12.dp)
                )
            } else {
                Text(
                    text = "🖼️",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 12.dp),
                    color = Color.Gray
                )
            }

            Column {
                Text(
                    "$emoji ${ingredient.nome}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    ingredient.descricao,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
                Text(
                    stringResource(R.string.raridade, ingredient.raridade),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }
    }
}