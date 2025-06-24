package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Entity

/**
 * Entidade que representa a relação muitos-para-muitos entre receitas e ingredientes.
 *
 * Cada instância associa um ingrediente a uma receita específica.
 *
 * @property receitaId ID da receita associada
 * @property ingredienteId ID do ingrediente associado
 */
@Entity(primaryKeys = ["receitaId", "ingredienteId"])
data class IngredienteReceitaCrossRef(
    val receitaId: Int,
    val ingredienteId: Int
)