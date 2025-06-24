package com.juliabolting.potioncrafterapp.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.juliabolting.potioncrafterapp.data.model.IngredienteReceitaCrossRef;
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
public final class IngredientRecipeDAO_Impl implements IngredientRecipeDAO {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<IngredienteReceitaCrossRef> __insertAdapterOfIngredienteReceitaCrossRef;

  public IngredientRecipeDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfIngredienteReceitaCrossRef = new EntityInsertAdapter<IngredienteReceitaCrossRef>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `IngredienteReceitaCrossRef` (`receitaId`,`ingredienteId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final IngredienteReceitaCrossRef entity) {
        statement.bindLong(1, entity.getReceitaId());
        statement.bindLong(2, entity.getIngredienteId());
      }
    };
  }

  @Override
  public Object insert(final IngredienteReceitaCrossRef crossRef,
      final Continuation<? super Unit> $completion) {
    if (crossRef == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfIngredienteReceitaCrossRef.insert(_connection, crossRef);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getIngredientsForRecipe(final int receitaId,
      final Continuation<? super List<IngredienteReceitaCrossRef>> $completion) {
    final String _sql = "SELECT * FROM IngredienteReceitaCrossRef WHERE receitaId = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, receitaId);
        final int _columnIndexOfReceitaId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "receitaId");
        final int _columnIndexOfIngredienteId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ingredienteId");
        final List<IngredienteReceitaCrossRef> _result = new ArrayList<IngredienteReceitaCrossRef>();
        while (_stmt.step()) {
          final IngredienteReceitaCrossRef _item;
          final int _tmpReceitaId;
          _tmpReceitaId = (int) (_stmt.getLong(_columnIndexOfReceitaId));
          final int _tmpIngredienteId;
          _tmpIngredienteId = (int) (_stmt.getLong(_columnIndexOfIngredienteId));
          _item = new IngredienteReceitaCrossRef(_tmpReceitaId,_tmpIngredienteId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
