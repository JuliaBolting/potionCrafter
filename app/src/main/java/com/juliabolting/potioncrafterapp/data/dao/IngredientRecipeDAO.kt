package com.juliabolting.potioncrafterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef

/**
 * DAO responsável pelo relacionamento entre ingredientes e receitas.
 * Utiliza a entidade de junção [IngredienteReceitaCrossRef] para
 * representar a associação N para N entre ingredientes e receitas.
 */
@Dao
interface IngredientRecipeDAO {

    /**
     * Insere uma associação entre um ingrediente e uma receita.
     * Caso já exista uma associação com os mesmos IDs, ela será substituída.
     *
     * @param crossRef A entidade que representa a relação entre receita e ingrediente.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: IngredienteReceitaCrossRef)

    /**
     * Retorna todas as associações de ingredientes para uma receita específica.
     *
     * @param receitaId O ID da receita.
     * @return Uma lista de [IngredienteReceitaCrossRef] correspondentes à receita.
     */
    @Query("SELECT * FROM IngredienteReceitaCrossRef WHERE receitaId = :receitaId")
    suspend fun getIngredientsForRecipe(receitaId: Int): List<IngredienteReceitaCrossRef>
}