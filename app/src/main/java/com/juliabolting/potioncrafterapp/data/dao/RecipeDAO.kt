package com.juliabolting.potioncrafterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.juliabolting.potioncrafterapp.data.model.Recipe
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients

/**
 * DAO responsável pelas operações relacionadas à entidade [Recipe] e suas relações
 * com ingredientes. Fornece métodos para inserção e consulta de receitas.
 */
@Dao
interface RecipeDAO {

    /**
     * Insere uma nova receita no banco de dados.
     * Em caso de conflito (mesmo ID), a receita existente será substituída.
     *
     * @param recipe A receita a ser inserida.
     * @return O ID gerado para a receita inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    /**
     * Retorna todas as receitas cadastradas no banco de dados, sem incluir os ingredientes.
     *
     * @return Lista de objetos [Recipe].
     */
    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<Recipe>

    /**
     * Retorna todas as receitas juntamente com os ingredientes associados.
     * Utiliza transação para garantir a consistência dos dados relacionais.
     *
     * @return Lista de objetos [RecipeWithIngredients] contendo a receita e seus ingredientes.
     */
    @Transaction
    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipesWithIngredients(): List<RecipeWithIngredients>
}