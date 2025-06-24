package com.juliabolting.potioncrafterapp.data.database;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import com.juliabolting.potioncrafterapp.data.dao.IngredientDAO;
import com.juliabolting.potioncrafterapp.data.dao.IngredientDAO_Impl;
import com.juliabolting.potioncrafterapp.data.dao.IngredientRecipeDAO;
import com.juliabolting.potioncrafterapp.data.dao.IngredientRecipeDAO_Impl;
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO;
import com.juliabolting.potioncrafterapp.data.dao.PlayerDAO_Impl;
import com.juliabolting.potioncrafterapp.data.dao.PotionDAO;
import com.juliabolting.potioncrafterapp.data.dao.PotionDAO_Impl;
import com.juliabolting.potioncrafterapp.data.dao.RecipeDAO;
import com.juliabolting.potioncrafterapp.data.dao.RecipeDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PlayerDAO _playerDAO;

  private volatile IngredientDAO _ingredientDAO;

  private volatile PotionDAO _potionDAO;

  private volatile RecipeDAO _recipeDAO;

  private volatile IngredientRecipeDAO _ingredientRecipeDAO;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(2, "3c29a1ce3eed2058f07f5b56987b6d54", "049523a1d033278bbbe1f57d8e38b62f") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `Player` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `level` INTEGER NOT NULL, `xp` INTEGER NOT NULL, `idioma` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `Ingredient` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `descricao` TEXT NOT NULL, `imagem` TEXT NOT NULL, `raridade` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `Potion` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `descricao` TEXT NOT NULL, `efeito` TEXT NOT NULL, `imagem` TEXT NOT NULL, `dataCriacao` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `Recipe` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `descricao` TEXT NOT NULL, `resultado` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `IngredienteReceitaCrossRef` (`receitaId` INTEGER NOT NULL, `ingredienteId` INTEGER NOT NULL, PRIMARY KEY(`receitaId`, `ingredienteId`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3c29a1ce3eed2058f07f5b56987b6d54')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `Player`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `Ingredient`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `Potion`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `Recipe`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `IngredienteReceitaCrossRef`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsPlayer = new HashMap<String, TableInfo.Column>(5);
        _columnsPlayer.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayer.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayer.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayer.put("xp", new TableInfo.Column("xp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayer.put("idioma", new TableInfo.Column("idioma", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPlayer = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPlayer = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayer = new TableInfo("Player", _columnsPlayer, _foreignKeysPlayer, _indicesPlayer);
        final TableInfo _existingPlayer = TableInfo.read(connection, "Player");
        if (!_infoPlayer.equals(_existingPlayer)) {
          return new RoomOpenDelegate.ValidationResult(false, "Player(com.juliabolting.potioncrafterapp.data.model.Player).\n"
                  + " Expected:\n" + _infoPlayer + "\n"
                  + " Found:\n" + _existingPlayer);
        }
        final Map<String, TableInfo.Column> _columnsIngredient = new HashMap<String, TableInfo.Column>(5);
        _columnsIngredient.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("nome", new TableInfo.Column("nome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("descricao", new TableInfo.Column("descricao", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("imagem", new TableInfo.Column("imagem", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredient.put("raridade", new TableInfo.Column("raridade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysIngredient = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesIngredient = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIngredient = new TableInfo("Ingredient", _columnsIngredient, _foreignKeysIngredient, _indicesIngredient);
        final TableInfo _existingIngredient = TableInfo.read(connection, "Ingredient");
        if (!_infoIngredient.equals(_existingIngredient)) {
          return new RoomOpenDelegate.ValidationResult(false, "Ingredient(com.juliabolting.potioncrafterapp.data.model.Ingredient).\n"
                  + " Expected:\n" + _infoIngredient + "\n"
                  + " Found:\n" + _existingIngredient);
        }
        final Map<String, TableInfo.Column> _columnsPotion = new HashMap<String, TableInfo.Column>(6);
        _columnsPotion.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPotion.put("nome", new TableInfo.Column("nome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPotion.put("descricao", new TableInfo.Column("descricao", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPotion.put("efeito", new TableInfo.Column("efeito", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPotion.put("imagem", new TableInfo.Column("imagem", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPotion.put("dataCriacao", new TableInfo.Column("dataCriacao", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPotion = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPotion = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPotion = new TableInfo("Potion", _columnsPotion, _foreignKeysPotion, _indicesPotion);
        final TableInfo _existingPotion = TableInfo.read(connection, "Potion");
        if (!_infoPotion.equals(_existingPotion)) {
          return new RoomOpenDelegate.ValidationResult(false, "Potion(com.juliabolting.potioncrafterapp.data.model.Potion).\n"
                  + " Expected:\n" + _infoPotion + "\n"
                  + " Found:\n" + _existingPotion);
        }
        final Map<String, TableInfo.Column> _columnsRecipe = new HashMap<String, TableInfo.Column>(4);
        _columnsRecipe.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("nome", new TableInfo.Column("nome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("descricao", new TableInfo.Column("descricao", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecipe.put("resultado", new TableInfo.Column("resultado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysRecipe = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesRecipe = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecipe = new TableInfo("Recipe", _columnsRecipe, _foreignKeysRecipe, _indicesRecipe);
        final TableInfo _existingRecipe = TableInfo.read(connection, "Recipe");
        if (!_infoRecipe.equals(_existingRecipe)) {
          return new RoomOpenDelegate.ValidationResult(false, "Recipe(com.juliabolting.potioncrafterapp.data.model.Recipe).\n"
                  + " Expected:\n" + _infoRecipe + "\n"
                  + " Found:\n" + _existingRecipe);
        }
        final Map<String, TableInfo.Column> _columnsIngredienteReceitaCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsIngredienteReceitaCrossRef.put("receitaId", new TableInfo.Column("receitaId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredienteReceitaCrossRef.put("ingredienteId", new TableInfo.Column("ingredienteId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysIngredienteReceitaCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesIngredienteReceitaCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIngredienteReceitaCrossRef = new TableInfo("IngredienteReceitaCrossRef", _columnsIngredienteReceitaCrossRef, _foreignKeysIngredienteReceitaCrossRef, _indicesIngredienteReceitaCrossRef);
        final TableInfo _existingIngredienteReceitaCrossRef = TableInfo.read(connection, "IngredienteReceitaCrossRef");
        if (!_infoIngredienteReceitaCrossRef.equals(_existingIngredienteReceitaCrossRef)) {
          return new RoomOpenDelegate.ValidationResult(false, "IngredienteReceitaCrossRef(com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef).\n"
                  + " Expected:\n" + _infoIngredienteReceitaCrossRef + "\n"
                  + " Found:\n" + _existingIngredienteReceitaCrossRef);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Player", "Ingredient", "Potion", "Recipe", "IngredienteReceitaCrossRef");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "Player", "Ingredient", "Potion", "Recipe", "IngredienteReceitaCrossRef");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PlayerDAO.class, PlayerDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(IngredientDAO.class, IngredientDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(PotionDAO.class, PotionDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(RecipeDAO.class, RecipeDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(IngredientRecipeDAO.class, IngredientRecipeDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PlayerDAO playerDao() {
    if (_playerDAO != null) {
      return _playerDAO;
    } else {
      synchronized(this) {
        if(_playerDAO == null) {
          _playerDAO = new PlayerDAO_Impl(this);
        }
        return _playerDAO;
      }
    }
  }

  @Override
  public IngredientDAO ingredientDao() {
    if (_ingredientDAO != null) {
      return _ingredientDAO;
    } else {
      synchronized(this) {
        if(_ingredientDAO == null) {
          _ingredientDAO = new IngredientDAO_Impl(this);
        }
        return _ingredientDAO;
      }
    }
  }

  @Override
  public PotionDAO potionDao() {
    if (_potionDAO != null) {
      return _potionDAO;
    } else {
      synchronized(this) {
        if(_potionDAO == null) {
          _potionDAO = new PotionDAO_Impl(this);
        }
        return _potionDAO;
      }
    }
  }

  @Override
  public RecipeDAO recipeDao() {
    if (_recipeDAO != null) {
      return _recipeDAO;
    } else {
      synchronized(this) {
        if(_recipeDAO == null) {
          _recipeDAO = new RecipeDAO_Impl(this);
        }
        return _recipeDAO;
      }
    }
  }

  @Override
  public IngredientRecipeDAO ingredientRecipeDao() {
    if (_ingredientRecipeDAO != null) {
      return _ingredientRecipeDAO;
    } else {
      synchronized(this) {
        if(_ingredientRecipeDAO == null) {
          _ingredientRecipeDAO = new IngredientRecipeDAO_Impl(this);
        }
        return _ingredientRecipeDAO;
      }
    }
  }
}
