package com.juliabolting.potioncrafterapp.data.dao;

/**
 * DAO responsável pelo relacionamento entre ingredientes e receitas.
 * Utiliza a entidade de junção [IngredienteReceitaCrossRef] para
 * representar a associação N para N entre ingredientes e receitas.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/dao/IngredientRecipeDAO;", "", "getIngredientsForRecipe", "", "Lcom/juliabolting/potioncrafterapp/data/model/IngredienteReceitaCrossRef;", "receitaId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "crossRef", "(Lcom/juliabolting/potioncrafterapp/data/model/IngredienteReceitaCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface IngredientRecipeDAO {
    
    /**
     * Insere uma associação entre um ingrediente e uma receita.
     * Caso já exista uma associação com os mesmos IDs, ela será substituída.
     *
     * @param crossRef A entidade que representa a relação entre receita e ingrediente.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef crossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Retorna todas as associações de ingredientes para uma receita específica.
     *
     * @param receitaId O ID da receita.
     * @return Uma lista de [IngredienteReceitaCrossRef] correspondentes à receita.
     */
    @androidx.room.Query(value = "SELECT * FROM IngredienteReceitaCrossRef WHERE receitaId = :receitaId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getIngredientsForRecipe(int receitaId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef>> $completion);
}