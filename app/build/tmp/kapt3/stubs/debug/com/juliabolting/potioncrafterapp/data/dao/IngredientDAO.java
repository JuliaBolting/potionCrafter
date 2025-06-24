package com.juliabolting.potioncrafterapp.data.dao;

/**
 * Data Access Object (DAO) responsável por gerenciar as operações
 * relacionadas à tabela de ingredientes no banco de dados Room.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/dao/IngredientDAO;", "", "getAllIngredients", "", "Lcom/juliabolting/potioncrafterapp/data/model/Ingredient;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "ingredient", "(Lcom/juliabolting/potioncrafterapp/data/model/Ingredient;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface IngredientDAO {
    
    /**
     * Insere um novo ingrediente na tabela ou substitui um existente
     * em caso de conflito (por exemplo, IDs duplicados).
     *
     * @param ingredient O ingrediente a ser inserido ou atualizado.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.juliabolting.potioncrafterapp.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Recupera todos os ingredientes armazenados no banco de dados.
     *
     * @return Uma lista contendo todos os ingredientes registrados.
     */
    @androidx.room.Query(value = "SELECT * FROM ingredient")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllIngredients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.juliabolting.potioncrafterapp.data.model.Ingredient>> $completion);
}