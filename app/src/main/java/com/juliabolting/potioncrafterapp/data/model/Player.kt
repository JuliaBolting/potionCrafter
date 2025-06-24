package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa um jogador no jogo.
 *
 * @property id Identificador único do jogador (gerado automaticamente).
 * @property name Nome do jogador.
 * @property level Nível atual do jogador. Valor padrão é 1.
 * @property xp Pontos de experiência acumulados pelo jogador. Valor padrão é 0.
 * @property idioma Idioma preferido do jogador, representado por código (ex: "pt", "en", "es"). Valor padrão é "pt".
 */
@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val level: Int = 1,
    val xp: Int = 0,
    val idioma: String = "pt" // "pt", "en", "es"
)