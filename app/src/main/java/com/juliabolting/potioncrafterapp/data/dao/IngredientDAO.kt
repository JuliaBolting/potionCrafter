package com.juliabolting.potioncrafterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juliabolting.potioncrafterapp.data.model.Ingredient

/**
 * Data Access Object (DAO) responsável por gerenciar as operações
 * relacionadas à tabela de ingredientes no banco de dados Room.
 */
@Dao
interface IngredientDAO {

    /**
     * Insere um novo ingrediente na tabela ou substitui um existente
     * em caso de conflito (por exemplo, IDs duplicados).
     *
     * @param ingredient O ingrediente a ser inserido ou atualizado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient)

    /**
     * Recupera todos os ingredientes armazenados no banco de dados.
     *
     * @return Uma lista contendo todos os ingredientes registrados.
     */
    @Query("SELECT * FROM ingredient")
    suspend fun getAllIngredients(): List<Ingredient>
}