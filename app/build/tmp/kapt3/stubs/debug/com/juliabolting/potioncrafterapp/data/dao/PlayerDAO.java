package com.juliabolting.potioncrafterapp.data.dao;

/**
 * DAO responsável pelas operações de acesso aos dados da entidade [Player].
 * Fornece métodos para inserir, recuperar e atualizar jogadores no banco de dados.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\f"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/dao/PlayerDAO;", "", "getPlayerById", "Lcom/juliabolting/potioncrafterapp/data/model/Player;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "player", "(Lcom/juliabolting/potioncrafterapp/data/model/Player;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface PlayerDAO {
    
    /**
     * Insere um jogador no banco de dados.
     * Se já existir um jogador com o mesmo ID, ele será substituído.
     *
     * @param player O jogador a ser inserido.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.juliabolting.potioncrafterapp.data.model.Player player, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Recupera um jogador pelo seu ID.
     *
     * @param id O ID do jogador.
     * @return O objeto [Player] correspondente, ou `null` se não encontrado.
     */
    @androidx.room.Query(value = "SELECT * FROM player WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlayerById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.juliabolting.potioncrafterapp.data.model.Player> $completion);
    
    /**
     * Atualiza as informações de um jogador existente.
     *
     * @param player O jogador com os dados atualizados.
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.juliabolting.potioncrafterapp.data.model.Player player, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}