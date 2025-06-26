import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.juliabolting.potioncrafterapp.MainActivity
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients
import kotlinx.coroutines.launch

/**
 * Composable que exibe o Grimório de Receitas, listando as receitas com seus
 * ingredientes e descrições. Inclui botão para baixar o relatório em PDF.
 *
 * O relatório é gerado ao clicar no ícone de download, chamando a função
 * [generatePotionReport] para criar um PDF com todas as receitas e seus ingredientes.
 */
@Composable
fun RecipeBookScreen(onGoBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val recipeDao = db.recipeDao()

    // Estado que guarda a lista de receitas com ingredientes
    var recipes by remember { mutableStateOf<List<RecipeWithIngredients>>(emptyList()) }

    // Carrega as receitas do banco ao iniciar o Composable
    LaunchedEffect(Unit) {
        recipes = recipeDao.getAllRecipesWithIngredients()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Coroutine para ações assíncronas
        val coroutineScope = rememberCoroutineScope()

        // Botão no topo direito para gerar e baixar o relatório PDF
        IconButton(
            onClick = {
                coroutineScope.launch {
                    val playerDao = db.playerDao()
                    val player = playerDao.getPlayerById(1)
                    val recipes = recipeDao.getAllRecipesWithIngredients()
                    generatePotionReport(context, player, recipes)
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Baixar receitas"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Título da tela
            Text(
                text = stringResource(R.string.grim_rio_de_receitas),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 64.dp, bottom = 24.dp)
            )

            // Caso não haja receitas cadastradas
            if (recipes.isEmpty()) {
                Text(
                    text = stringResource(R.string.nenhuma_receita_encontrada),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                // Lista de receitas com seus detalhes
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(recipes) { recipe ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = recipe.recipe.nome,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = recipe.recipe.descricao,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Ingredientes: ${recipe.ingredientes.joinToString { it.nome }}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // FloatingActionButton para voltar à MainActivity
        FloatingActionButton(
            onClick = onGoBack,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 35.dp)
                .size(48.dp),
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar para a tela principal",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}