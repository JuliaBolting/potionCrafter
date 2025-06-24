package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Representa uma receita juntamente com seus ingredientes relacionados.
 *
 * Esta classe é usada para carregar uma receita e sua lista de ingredientes associados
 * por meio da relação many-to-many definida pela entidade [IngredienteReceitaCrossRef].
 *
 * @property recipe A receita principal ([Recipe]).
 * @property ingredientes Lista dos ingredientes ([Ingredient]) associados à receita.
 */
data class RecipeWithIngredients(
    @Embedded val recipe: Recipe,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = IngredienteReceitaCrossRef::class,
            parentColumn = "receitaId",
            entityColumn = "ingredienteId"
        )
    )
    val ingredientes: List<Ingredient>
)