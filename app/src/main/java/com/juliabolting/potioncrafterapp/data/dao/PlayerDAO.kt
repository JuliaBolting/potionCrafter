package com.juliabolting.potioncrafterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.juliabolting.potioncrafterapp.data.model.Player

/**
 * DAO responsável pelas operações de acesso aos dados da entidade [Player].
 * Fornece métodos para inserir, recuperar e atualizar jogadores no banco de dados.
 */
@Dao
interface PlayerDAO {

    /**
     * Insere um jogador no banco de dados.
     * Se já existir um jogador com o mesmo ID, ele será substituído.
     *
     * @param player O jogador a ser inserido.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player): Long

    /**
     * Recupera um jogador pelo seu ID.
     *
     * @param id O ID do jogador.
     * @return O objeto [Player] correspondente, ou `null` se não encontrado.
     */
    @Query("SELECT * FROM player WHERE id = :id")
    suspend fun getPlayerById(id: Int): Player?

    /**
     * Atualiza as informações de um jogador existente.
     *
     * @param player O jogador com os dados atualizados.
     */
    @Update
    suspend fun update(player: Player)
}