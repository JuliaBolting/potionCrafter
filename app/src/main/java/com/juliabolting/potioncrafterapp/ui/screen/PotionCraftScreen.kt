package com.juliabolting.potioncrafterapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.Ingredient
import com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef
import com.juliabolting.potioncrafterapp.data.model.Potion
import com.juliabolting.potioncrafterapp.data.model.Recipe
import kotlinx.coroutines.launch

/**
 * Composable que exibe a tela para criação de poções a partir da seleção
 * de até três ingredientes. Permite o usuário escolher ingredientes,
 * criar uma receita, salvar no banco de dados e mostrar um diálogo
 * confirmando a criação.
 *
 * @param onCrafted Callback chamado após a poção ser criada com sucesso.
 */
@Composable
fun PotionCraftAppScreen(
    onCrafted: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val ingredientDao = db.ingredientDao()
    val recipeDao = db.recipeDao()
    val crossRefDao = db.ingredientRecipeDao()
    val coroutineScope = rememberCoroutineScope()

    // Estado dos ingredientes disponíveis no banco
    var allIngredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }
    // Estado dos ingredientes selecionados pelo usuário
    var selectedIngredients by remember { mutableStateOf(setOf<Ingredient>()) }
    // Controla exibição do diálogo de resultado
    var showResultDialog by remember { mutableStateOf(false) }

    // Busca os ingredientes no banco ao carregar o Composable
    LaunchedEffect(Unit) {
        allIngredients = ingredientDao.getAllIngredients()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            stringResource(R.string.escolha_at_3_ingredientes),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFEEEEEE)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(allIngredients.size) { index ->
                val ingredient = allIngredients[index]
                val isSelected = ingredient in selectedIngredients
                // Permite selecionar até 3 ingredientes
                val canSelect = selectedIngredients.size < 3 || isSelected

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(ingredient.nome, color = Color(0xFFEEEEEE))
                    Checkbox(checked = isSelected, onCheckedChange = null)
                }
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    val ingredientes = selectedIngredients.toList()
                    val nomeReceita = ingredientes.joinToString(" + ") { it.nome }

                    // Cria uma nova receita com nome e descrição baseados nos ingredientes
                    val recipe = Recipe(
                        nome = nomeReceita,
                        descricao = context.getString(
                            R.string.po_o_criada_com_ingredientes,
                            ingredientes.size
                        ),
                        resultado = nomeReceita
                    )
                    // Insere no banco e obtém o id da receita criada
                    val recipeIdLong = recipeDao.insert(recipe).toString()
                    val recipeId = recipeIdLong.toInt()

                    // Insere relação receita-ingrediente para cada ingrediente selecionado
                    ingredientes.forEach {
                        crossRefDao.insert(
                            IngredienteReceitaCrossRef(
                                receitaId = recipeId,
                                ingredienteId = it.id
                            )
                        )
                    }

                    showResultDialog = true
                    onCrafted()
                }
            },
            enabled = selectedIngredients.isNotEmpty()
        ) {
            Text(stringResource(R.string.criar_po_o))
        }
    }

    // Diálogo que aparece após a criação da poção, mostrando os ingredientes usados
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
                Text(
                    stringResource(
                        R.string.sua_po_o_cont_m,
                        selectedIngredients.joinToString(", ") { it.nome }
                    )
                )
            }
        )
    }
}