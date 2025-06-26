package com.juliabolting.potioncrafterapp.ui.screen

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.juliabolting.potioncrafterapp.data.model.Player
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Gera um relatório em PDF listando as poções criadas, seus ingredientes e um gráfico
 * de barras mostrando a contagem de ingredientes por raridade.
 *
 * O PDF é salvo na pasta de downloads do dispositivo usando MediaStore.
 *
 * @param context Contexto da aplicação para acesso a recursos e diretórios
 * @param player Jogador atual (opcional)
 * @param recipes Lista de [RecipeWithIngredients] para gerar o relatório
 */
@RequiresApi(Build.VERSION_CODES.Q)
suspend fun generatePotionReport(
    context: Context,
    player: Player?,
    recipes: List<RecipeWithIngredients>
) {
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val pageWidth = 595 // A4 width in points (1/72 inch)
    val pageHeight = 842 // A4 height in points
    val marginLeft = 60f
    val maxContentHeight = 750f // Leave space for footer

    var y = 50f
    var pageNumber = 1

    // Função para criar nova página
    fun newPage(): PdfDocument.Page {
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber++).create()
        val page = pdfDocument.startPage(pageInfo)
        y = 50f
        return page
    }

    // Cria primeira página
    var page = newPage()
    val canvas = page.canvas

    // --- Cabeçalho ---
    paint.textSize = 18f
    paint.isFakeBoldText = true
    canvas.drawText("📜 Grimório de Poções Criadas", marginLeft, y, paint)
    y += 24f

    paint.textSize = 12f
    paint.isFakeBoldText = false
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val currentDateTime = dateFormat.format(Date())
    canvas.drawText("Gerado em: $currentDateTime", marginLeft, y, paint)
    y += 20f

    // Informações do jogador, se disponível
    player?.let {
        canvas.drawText("Jogador: ${it.name}  |  Nível: ${it.level}  |  XP: ${it.xp}", marginLeft, y, paint)
        y += 30f
    }

    paint.textSize = 14f
    val raridadeCount = mutableMapOf<String, Int>()

    for ((index, recipe) in recipes.withIndex()) {
        // Se ultrapassou o limite vertical, termina a página e inicia uma nova
        if (y > maxContentHeight) {
            pdfDocument.finishPage(page)
            page = newPage()
        }
        val c = page.canvas

        paint.isFakeBoldText = true
        c.drawText("Poção ${index + 1}: ${recipe.recipe.nome}", marginLeft, y, paint)
        y += 18f

        paint.isFakeBoldText = false
        c.drawText("Descrição: ${recipe.recipe.descricao}", marginLeft + 20f, y, paint)
        y += 18f

        c.drawText("Ingredientes:", marginLeft + 20f, y, paint)
        y += 16f

        for (ingredient in recipe.ingredientes) {
            c.drawText("- ${ingredient.nome} (${ingredient.raridade})", marginLeft + 40f, y, paint)
            y += 16f
            raridadeCount[ingredient.raridade] = raridadeCount.getOrDefault(ingredient.raridade, 0) + 1
        }

        y += 12f
        c.drawLine(marginLeft, y, pageWidth - marginLeft, y, paint)
        y += 20f
    }

    // Gráfico (também pode precisar de nova página)
    if (y > maxContentHeight) {
        pdfDocument.finishPage(page)
        page = newPage()
    }
    val c = page.canvas
    y += 10f
    paint.textSize = 16f
    paint.isFakeBoldText = true
    c.drawText("📊 Ingredientes por Raridade:", marginLeft, y, paint)
    y += 25f

    paint.textSize = 13f
    paint.isFakeBoldText = false

    val barMaxWidth = 300f
    val maxCount = raridadeCount.values.maxOrNull()?.toFloat()?.takeIf { it > 0 } ?: 1f

    for ((raridade, count) in raridadeCount) {
        c.drawText(raridade, marginLeft, y, paint)
        val barLength = (count / maxCount) * barMaxWidth
        c.drawRect(marginLeft + 90f, y - 12f, marginLeft + 90f + barLength, y - 2f, paint)
        c.drawText("($count)", marginLeft + 400f, y, paint)
        y += 25f
    }

    // Rodapé total poções
    if (y > maxContentHeight) {
        pdfDocument.finishPage(page)
        page = newPage()
    }
    page.canvas.drawText("Total de poções: ${recipes.size}", marginLeft, 820f, paint)

    pdfDocument.finishPage(page)

    // Salva o PDF usando MediaStore
    val fileName = "relatorio_pocoes_${System.currentTimeMillis()}.pdf"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        try {
            resolver.openOutputStream(uri)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "PDF salvo em: $uri", Toast.LENGTH_LONG).show()
                }
                Log.d("PotionCrafter", "PDF salvo em: $uri")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Erro ao salvar PDF: ${e.message}", Toast.LENGTH_LONG).show()
            }
            Log.e("PotionCrafter", "Erro ao salvar PDF: ${e.message}", e)
        }
    } else {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Falha ao criar URI para o PDF", Toast.LENGTH_LONG).show()
        }
        Log.e("PotionCrafter", "Falha ao criar URI para o PDF")
    }

    pdfDocument.close()
}