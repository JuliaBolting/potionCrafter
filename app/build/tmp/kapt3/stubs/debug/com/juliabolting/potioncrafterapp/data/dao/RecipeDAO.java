package com.juliabolting.potioncrafterapp.data.dao;

/**
 * DAO responsável pelas operações relacionadas à entidade [Recipe] e suas relações
 * com ingredientes. Fornece métodos para inserção e consulta de receitas.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/dao/RecipeDAO;", "", "getAllRecipes", "", "Lcom/juliabolting/potioncrafterapp/data/model/Recipe;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRecipesWithIngredients", "Lcom/juliabolting/potioncrafterapp/data/model/RecipeWithIngredients;", "insert", "", "recipe", "(Lcom/juliabolting/potioncrafterapp/data/model/Recipe;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface RecipeDAO {
    
    /**
     * Insere uma nova receita no banco de dados.
     * Em caso de conflito (mesmo ID), a receita existente será substituída.
     *
     * @param recipe A receita a ser inserida.
     * @return O ID gerado para a receita inserida.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.juliabolting.potioncrafterapp.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Retorna todas as receitas cadastradas no banco de dados, sem incluir os ingredientes.
     *
     * @return Lista de objetos [Recipe].
     */
    @androidx.room.Query(value = "SELECT * FROM recipe")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllRecipes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.juliabolting.potioncrafterapp.data.model.Recipe>> $completion);
    
    /**
     * Retorna todas as receitas juntamente com os ingredientes associados.
     * Utiliza transação para garantir a consistência dos dados relacionais.
     *
     * @return Lista de objetos [RecipeWithIngredients] contendo a receita e seus ingredientes.
     */
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM recipe")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllRecipesWithIngredients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients>> $completion);
}