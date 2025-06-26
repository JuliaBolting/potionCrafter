package com.juliabolting.potioncrafterapp.data.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juliabolting.potioncrafterapp.data.dao.IngredientDAO
import com.juliabolting.potioncrafterapp.data.dao.IngredientRecipeDAO
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO
import com.juliabolting.potioncrafterapp.data.dao.PotionDAO
import com.juliabolting.potioncrafterapp.data.dao.RecipeDAO
import com.juliabolting.potioncrafterapp.data.model.Ingredient
import com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef
import com.juliabolting.potioncrafterapp.data.model.Player
import com.juliabolting.potioncrafterapp.data.model.Potion
import com.juliabolting.potioncrafterapp.data.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

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
@Database(
    entities = [Player::class, Ingredient::class, Potion::class, Recipe::class, IngredienteReceitaCrossRef::class],
    version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /** Retorna o DAO para operações com jogadores. */
    abstract fun playerDao(): PlayerDAO

    /** Retorna o DAO para operações com ingredientes. */
    abstract fun ingredientDao(): IngredientDAO

    /** Retorna o DAO para operações com poções. */
    abstract fun potionDao(): PotionDAO

    /** Retorna o DAO para operações com receitas. */
    abstract fun recipeDao(): RecipeDAO

    /** Retorna o DAO para operações com a relação ingrediente-receita. */
    abstract fun ingredientRecipeDao(): IngredientRecipeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retorna a instância singleton do banco de dados.
         * Cria e configura o banco caso ainda não exista.
         *
         * Inicializa ingredientes padrão a partir do arquivo JSON na primeira criação do banco.
         *
         * @param context Contexto da aplicação
         * @return Instância do [AppDatabase]
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "potioncrafter.db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d(
                                "AppDatabase",
                                "Banco de dados criado! Inserindo ingredientes iniciais."
                            )
                            val locale = Locale.getDefault().language

                            CoroutineScope(Dispatchers.IO).launch {
                                val ingredients = loadInitialIngredients(context, locale)
                                INSTANCE?.ingredientDao()?.apply {
                                    ingredients.forEach { insert(it) }
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }

        /**
         * Carrega os ingredientes iniciais a partir do arquivo `ingredients.json` presente em assets.
         * Leva em conta o idioma do usuário para escolher o arquivo json com os ingredientes.
         *
         * @param context Contexto da aplicação
         * @return Lista de [Ingredient] carregados do JSON
         */
        fun loadInitialIngredients(context: Context, locale: String): List<Ingredient> {
            val filename = when (locale) {
                "en" -> "ingredients_en.json"
                "pt" -> "ingredients.json"
                "es" -> "ingredients_es.json"
                else -> "ingredients.json"
            }
            Log.d("PotionCrafter", "3 Ingredientes filename: ${filename}")
            val inputStream = context.assets.open(filename)
            val json = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val type = object : TypeToken<List<Ingredient>>() {}.type
            return gson.fromJson(json, type)
        }
    }
}