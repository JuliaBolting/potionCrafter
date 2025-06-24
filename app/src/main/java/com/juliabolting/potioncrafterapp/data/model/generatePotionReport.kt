import android.content.Context
import android.os.Environment
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.widget.Toast
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Gera um relatório em PDF listando as poções criadas, seus ingredientes e um gráfico
 * de barras mostrando a contagem de ingredientes por raridade.
 *
 * O PDF é salvo na pasta de downloads do dispositivo.
 *
 * @param context Contexto da aplicação para acesso a recursos e diretórios
 * @param recipes Lista de [RecipeWithIngredients] para gerar o relatório
 */
import com.juliabolting.potioncrafterapp.data.model.Player

fun generatePotionReport(
    context: Context,
    player: Player?,
    recipes: List<RecipeWithIngredients>
) {
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val pageWidth = 595
    val pageHeight = 842
    val marginLeft = 60f
    val maxContentHeight = 750f // deixa espaço no rodapé

    var y = 50f
    var pageNumber = 1

    // Função para criar página nova
    fun newPage(): PdfDocument.Page {
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber++).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
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

    // Rodapé total poções (se ultrapassou a página, cria nova)
    if (y > maxContentHeight) {
        pdfDocument.finishPage(page)
        page = newPage()
    }
    page.canvas.drawText("Total de poções: ${recipes.size}", marginLeft, 820f, paint)

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, "relatorio_pocoes.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF salvo em: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Erro ao salvar PDF", Toast.LENGTH_LONG).show()
    } finally {
        pdfDocument.close()
    }
}
