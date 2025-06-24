package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa uma receita para criação de poção.
 *
 * @property id Identificador único da receita (gerado automaticamente).
 * @property nome Nome da receita.
 * @property descricao Descrição detalhada da receita.
 * @property resultado Nome da poção que será criada ao seguir esta receita.
 */
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String,
    val resultado: String
)