package com.juliabolting.potioncrafterapp.data.dao;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.juliabolting.potioncrafterapp.data.model.Ingredient;
import com.juliabolting.potioncrafterapp.data.model.Recipe;
import com.juliabolting.potioncrafterapp.data.model.RecipeWithIngredients;
import java.lang.Class;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class RecipeDAO_Impl implements RecipeDAO {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Recipe> __insertAdapterOfRecipe;

  public RecipeDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfRecipe = new EntityInsertAdapter<Recipe>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Recipe` (`id`,`nome`,`descricao`,`resultado`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Recipe entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNome() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getNome());
        }
        if (entity.getDescricao() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescricao());
        }
        if (entity.getResultado() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getResultado());
        }
      }
    };
  }

  @Override
  public Object insert(final Recipe recipe, final Continuation<? super Long> arg1) {
    if (recipe == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfRecipe.insertAndReturnId(_connection, recipe);
    }, arg1);
  }

  @Override
  public Object getAllRecipes(final Continuation<? super List<Recipe>> arg0) {
    final String _sql = "SELECT * FROM recipe";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNome = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nome");
        final int _columnIndexOfDescricao = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descricao");
        final int _columnIndexOfResultado = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "resultado");
        final List<Recipe> _result = new ArrayList<Recipe>();
        while (_stmt.step()) {
          final Recipe _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpNome;
          if (_stmt.isNull(_columnIndexOfNome)) {
            _tmpNome = null;
          } else {
            _tmpNome = _stmt.getText(_columnIndexOfNome);
          }
          final String _tmpDescricao;
          if (_stmt.isNull(_columnIndexOfDescricao)) {
            _tmpDescricao = null;
          } else {
            _tmpDescricao = _stmt.getText(_columnIndexOfDescricao);
          }
          final String _tmpResultado;
          if (_stmt.isNull(_columnIndexOfResultado)) {
            _tmpResultado = null;
          } else {
            _tmpResultado = _stmt.getText(_columnIndexOfResultado);
          }
          _item = new Recipe(_tmpId,_tmpNome,_tmpDescricao,_tmpResultado);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, arg0);
  }

  @Override
  public Object getAllRecipesWithIngredients(
      final Continuation<? super List<RecipeWithIngredients>> arg0) {
    final String _sql = "SELECT * FROM recipe";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNome = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nome");
        final int _columnIndexOfDescricao = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descricao");
        final int _columnIndexOfResultado = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "resultado");
        final LongSparseArray<ArrayList<Ingredient>> _collectionIngredientes = new LongSparseArray<ArrayList<Ingredient>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionIngredientes.containsKey(_tmpKey)) {
            _collectionIngredientes.put(_tmpKey, new ArrayList<Ingredient>());
          }
        }
        _stmt.reset();
        __fetchRelationshipIngredientAscomJuliaboltingPotioncrafterappDataModelIngredient(_connection, _collectionIngredientes);
        final List<RecipeWithIngredients> _result = new ArrayList<RecipeWithIngredients>();
        while (_stmt.step()) {
          final RecipeWithIngredients _item;
          final Recipe _tmpRecipe;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpNome;
          if (_stmt.isNull(_columnIndexOfNome)) {
            _tmpNome = null;
          } else {
            _tmpNome = _stmt.getText(_columnIndexOfNome);
          }
          final String _tmpDescricao;
          if (_stmt.isNull(_columnIndexOfDescricao)) {
            _tmpDescricao = null;
          } else {
            _tmpDescricao = _stmt.getText(_columnIndexOfDescricao);
          }
          final String _tmpResultado;
          if (_stmt.isNull(_columnIndexOfResultado)) {
            _tmpResultado = null;
          } else {
            _tmpResultado = _stmt.getText(_columnIndexOfResultado);
          }
          _tmpRecipe = new Recipe(_tmpId,_tmpNome,_tmpDescricao,_tmpResultado);
          final ArrayList<Ingredient> _tmpIngredientesCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpIngredientesCollection = _collectionIngredientes.get(_tmpKey_1);
          _item = new RecipeWithIngredients(_tmpRecipe,_tmpIngredientesCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipIngredientAscomJuliaboltingPotioncrafterappDataModelIngredient(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<Ingredient>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshipIngredientAscomJuliaboltingPotioncrafterappDataModelIngredient(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `Ingredient`.`id` AS `id`,`Ingredient`.`nome` AS `nome`,`Ingredient`.`descricao` AS `descricao`,`Ingredient`.`imagem` AS `imagem`,`Ingredient`.`raridade` AS `raridade`,_junction.`receitaId` FROM `IngredienteReceitaCrossRef` AS _junction INNER JOIN `Ingredient` ON (_junction.`ingredienteId` = `Ingredient`.`id`) WHERE _junction.`receitaId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SQLiteStatement _stmt = _connection.prepare(_sql);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    try {
      // _junction.receitaId;
      final int _itemKeyIndex = 5;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfId = 0;
      final int _columnIndexOfNome = 1;
      final int _columnIndexOfDescricao = 2;
      final int _columnIndexOfImagem = 3;
      final int _columnIndexOfRaridade = 4;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<Ingredient> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Ingredient _item_1;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpNome;
          if (_stmt.isNull(_columnIndexOfNome)) {
            _tmpNome = null;
          } else {
            _tmpNome = _stmt.getText(_columnIndexOfNome);
          }
          final String _tmpDescricao;
          if (_stmt.isNull(_columnIndexOfDescricao)) {
            _tmpDescricao = null;
          } else {
            _tmpDescricao = _stmt.getText(_columnIndexOfDescricao);
          }
          final String _tmpImagem;
          if (_stmt.isNull(_columnIndexOfImagem)) {
            _tmpImagem = null;
          } else {
            _tmpImagem = _stmt.getText(_columnIndexOfImagem);
          }
          final String _tmpRaridade;
          if (_stmt.isNull(_columnIndexOfRaridade)) {
            _tmpRaridade = null;
          } else {
            _tmpRaridade = _stmt.getText(_columnIndexOfRaridade);
          }
          _item_1 = new Ingredient(_tmpId,_tmpNome,_tmpDescricao,_tmpImagem,_tmpRaridade);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
