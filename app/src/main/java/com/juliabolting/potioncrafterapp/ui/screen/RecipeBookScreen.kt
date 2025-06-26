package com.juliabolting.potioncrafterapp.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.juliabolting.potioncrafterapp.R
import com.juliabolting.potioncrafterapp.data.database.AppDatabase
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients
import kotlinx.coroutines.launch

/**
 * Composable que exibe o Grimório de Receitas, listando as receitas com seus
 * ingredientes e descrições. Inclui botão para baixar o relatório em PDF.
 */
@Composable
@RequiresApi(Build.VERSION_CODES.Q)
fun RecipeBookScreen(onGoBack: () -> Unit, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    Log.d("RecipeBookScreen", "Context class: ${context.javaClass.simpleName}")

    val db = remember { AppDatabase.getInstance(context) }
    val recipeDao = db.recipeDao()

    var recipes by remember { mutableStateOf<List<RecipeWithIngredients>>(emptyList()) }

    LaunchedEffect(Unit) {
        recipes = recipeDao.getAllRecipesWithIngredients()
    }

    val permission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    } else {
        null
    }

    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("PermissionResult", "Permission granted: $isGranted")
        if (isGranted || permission == null) {
            coroutineScope.launch {
                val playerDao = db.playerDao()
                val player = playerDao.getPlayerById(1)
                val recipesList = recipeDao.getAllRecipesWithIngredients()
                generatePotionReport(context, player, recipesList)
                snackbarHostState.showSnackbar(
                    message = "PDF gerado com sucesso!",
                    actionLabel = null,
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Permissão negada. O PDF não pode ser gerado.",
                    actionLabel = null,
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    fun requestStoragePermission() {
        permission?.let {
            Log.d("RequestPermission", "Launching permission request for: $it")
            permissionLauncher.launch(it)
        } ?: run {
            Log.d("RequestPermission", "No permission required for this Android version")
        }
    }

    fun hasPermission(): Boolean {
        val result = permission?.let {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        } ?: true
        Log.d("HasPermission", "Permission check result: $result")
        return result
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                Log.d("IconButtonClick", "Button clicked, checking permission")
                if (hasPermission()) {
                    Log.d(
                        "hasPermission",
                        "Permission already granted or not required, generating PDF"
                    )
                    coroutineScope.launch {
                        val playerDao = db.playerDao()
                        val player = playerDao.getPlayerById(1)
                        val recipesList = recipeDao.getAllRecipesWithIngredients()
                        generatePotionReport(context, player, recipesList)
                        snackbarHostState.showSnackbar(
                            message = "PDF gerado com sucesso!",
                            actionLabel = null,
                            withDismissAction = false,
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    Log.d("requestStoragePermission", "Requesting permission")
                    requestStoragePermission()
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

            if (recipes.isEmpty()) {
                Text(
                    text = stringResource(R.string.nenhuma_receita_encontrada),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
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
                                    text = stringResource(
                                        R.string.ingredientes,
                                        recipe.ingredientes.joinToString { it.nome }
                                    ),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onGoBack,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 45.dp)
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