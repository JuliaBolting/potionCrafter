package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa um ingrediente usado na criação de poções.
 *
 * @property id Identificador único do ingrediente (gerado automaticamente pelo banco de dados)
 * @property nome Nome do ingrediente
 * @property descricao Descrição detalhada do ingrediente
 * @property imagem Caminho ou URI para a imagem do ingrediente
 * @property raridade Categoria de raridade do ingrediente (ex: comum, raro, épico)
 */
@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String,
    val imagem: String, // caminho ou URI da imagem
    val raridade: String // comum, raro, épico etc.
)