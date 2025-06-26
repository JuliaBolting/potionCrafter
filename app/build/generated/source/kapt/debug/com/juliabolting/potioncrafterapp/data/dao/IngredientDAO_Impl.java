package com.juliabolting.potioncrafterapp.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.juliabolting.potioncrafterapp.data.model.Ingredient;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class IngredientDAO_Impl implements IngredientDAO {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Ingredient> __insertAdapterOfIngredient;

  public IngredientDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfIngredient = new EntityInsertAdapter<Ingredient>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Ingredient` (`id`,`nome`,`descricao`,`imagem`,`raridade`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Ingredient entity) {
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
        if (entity.getImagem() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getImagem());
        }
        if (entity.getRaridade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getRaridade());
        }
      }
    };
  }

  @Override
  public Object insert(final Ingredient ingredient, final Continuation<? super Unit> arg1) {
    if (ingredient == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfIngredient.insert(_connection, ingredient);
      return Unit.INSTANCE;
    }, arg1);
  }

  @Override
  public Object getAllIngredients(final Continuation<? super List<Ingredient>> arg0) {
    final String _sql = "SELECT * FROM ingredient";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNome = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nome");
        final int _columnIndexOfDescricao = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descricao");
        final int _columnIndexOfImagem = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagem");
        final int _columnIndexOfRaridade = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "raridade");
        final List<Ingredient> _result = new ArrayList<Ingredient>();
        while (_stmt.step()) {
          final Ingredient _item;
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
          _item = new Ingredient(_tmpId,_tmpNome,_tmpDescricao,_tmpImagem,_tmpRaridade);
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
}
