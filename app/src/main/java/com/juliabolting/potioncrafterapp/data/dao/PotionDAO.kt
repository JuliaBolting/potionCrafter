package com.juliabolting.potioncrafterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juliabolting.potioncrafterapp.data.model.Potion

/**
 * DAO responsável pelas operações de acesso aos dados da entidade [Potion].
 * Fornece métodos para inserir e recuperar poções do banco de dados.
 */
@Dao
interface PotionDAO {

    /**
     * Insere uma poção no banco de dados.
     * Se já existir uma poção com o mesmo ID, ela será substituída.
     *
     * @param potion A poção a ser inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(potion: Potion)

    /**
     * Recupera todas as poções armazenadas no banco de dados.
     *
     * @return Uma lista com todas as poções.
     */
    @Query("SELECT * FROM potion")
    suspend fun getAllPotions(): List<Potion>
}