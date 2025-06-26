package com.juliabolting.potioncrafterapp.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juliabolting.potioncrafterapp.ui.theme.PotionCrafterAppTheme

/**
 * Activity principal para a tela de criação de poções usando Jetpack Compose.
 *
 * Define o tema da aplicação e exibe o conteúdo da UI da tela de criação de poções.
 *
 * @property onCrafted Callback que pode ser usado para tratar evento quando uma poção é criada.
 */
class PotionCraftActivity : ComponentActivity() {

    /**
     * Método chamado na criação da Activity, responsável por configurar o conteúdo da UI
     * utilizando Compose, dentro do tema definido na aplicação.
     *
     * @param savedInstanceState Estado salvo anteriormente da Activity, se houver.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotionCrafterAppTheme {
                //PotionCraftAppScreen(onGoBack = { currentScreen.value = "main")
            }
        }
    }
}