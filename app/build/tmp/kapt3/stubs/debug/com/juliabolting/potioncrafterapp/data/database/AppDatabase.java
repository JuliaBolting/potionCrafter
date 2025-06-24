package com.juliabolting.potioncrafterapp.data.database;

/**
 * Classe abstrata que representa o banco de dados do aplicativo PotionCrafter.
 * Utiliza Room para gerenciamento do banco SQLite.
 *
 * Contém as entidades [Player], [Ingredient], [Potion], [Recipe] e a relação cruzada
 * [IngredienteReceitaCrossRef].
 *
 * Versão do banco: 2
 * Exporta esquema: falso
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&\u00a8\u0006\u000e"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "ingredientDao", "Lcom/juliabolting/potioncrafterapp/data/dao/IngredientDAO;", "ingredientRecipeDao", "Lcom/juliabolting/potioncrafterapp/data/dao/IngredientRecipeDAO;", "playerDao", "Lcom/juliabolting/potioncrafterapp/data/dao/PlayerDAO;", "potionDao", "Lcom/juliabolting/potioncrafterapp/data/dao/PotionDAO;", "recipeDao", "Lcom/juliabolting/potioncrafterapp/data/dao/RecipeDAO;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.juliabolting.potioncrafterapp.data.model.Player.class, com.juliabolting.potioncrafterapp.data.model.Ingredient.class, com.juliabolting.potioncrafterapp.data.model.Potion.class, com.juliabolting.potioncrafterapp.data.model.Recipe.class, com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.juliabolting.potioncrafterapp.data.database.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.juliabolting.potioncrafterapp.data.database.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    /**
     * Retorna o DAO para operações com jogadores.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.juliabolting.potioncrafterapp.data.dao.PlayerDAO playerDao();
    
    /**
     * Retorna o DAO para operações com ingredientes.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.juliabolting.potioncrafterapp.data.dao.IngredientDAO ingredientDao();
    
    /**
     * Retorna o DAO para operações com poções.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.juliabolting.potioncrafterapp.data.dao.PotionDAO potionDao();
    
    /**
     * Retorna o DAO para operações com receitas.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.juliabolting.potioncrafterapp.data.dao.RecipeDAO recipeDao();
    
    /**
     * Retorna o DAO para operações com a relação ingrediente-receita.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.juliabolting.potioncrafterapp.data.dao.IngredientRecipeDAO ingredientRecipeDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/juliabolting/potioncrafterapp/data/database/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/juliabolting/potioncrafterapp/data/database/AppDatabase;", "getInstance", "context", "Landroid/content/Context;", "loadInitialIngredients", "", "Lcom/juliabolting/potioncrafterapp/data/model/Ingredient;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Retorna a instância singleton do banco de dados.
         * Cria e configura o banco caso ainda não exista.
         *
         * Inicializa ingredientes padrão a partir do arquivo JSON na primeira criação do banco.
         *
         * @param context Contexto da aplicação
         * @return Instância do [AppDatabase]
         */
        @org.jetbrains.annotations.NotNull()
        public final com.juliabolting.potioncrafterapp.data.database.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        /**
         * Carrega os ingredientes iniciais a partir do arquivo `ingredients.json` presente em assets.
         *
         * @param context Contexto da aplicação
         * @return Lista de [Ingredient] carregados do JSON
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.juliabolting.potioncrafterapp.data.model.Ingredient> loadInitialIngredients(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}