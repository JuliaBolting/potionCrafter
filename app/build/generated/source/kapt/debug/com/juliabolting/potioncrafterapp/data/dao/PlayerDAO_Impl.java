package com.juliabolting.potioncrafterapp.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.juliabolting.potioncrafterapp.data.model.Player;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class PlayerDAO_Impl implements PlayerDAO {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Player> __insertAdapterOfPlayer;

  private final EntityDeleteOrUpdateAdapter<Player> __updateAdapterOfPlayer;

  public PlayerDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPlayer = new EntityInsertAdapter<Player>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Player` (`id`,`name`,`level`,`xp`,`idioma`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Player entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        statement.bindLong(3, entity.getLevel());
        statement.bindLong(4, entity.getXp());
        if (entity.getIdioma() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getIdioma());
        }
      }
    };
    this.__updateAdapterOfPlayer = new EntityDeleteOrUpdateAdapter<Player>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Player` SET `id` = ?,`name` = ?,`level` = ?,`xp` = ?,`idioma` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Player entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        statement.bindLong(3, entity.getLevel());
        statement.bindLong(4, entity.getXp());
        if (entity.getIdioma() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getIdioma());
        }
        statement.bindLong(6, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Player player, final Continuation<? super Unit> $completion) {
    if (player == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfPlayer.insert(_connection, player);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Player player, final Continuation<? super Unit> $completion) {
    if (player == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfPlayer.handle(_connection, player);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getPlayerById(final int id, final Continuation<? super Player> $completion) {
    final String _sql = "SELECT * FROM player WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfXp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "xp");
        final int _columnIndexOfIdioma = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "idioma");
        final Player _result;
        if (_stmt.step()) {
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final int _tmpLevel;
          _tmpLevel = (int) (_stmt.getLong(_columnIndexOfLevel));
          final int _tmpXp;
          _tmpXp = (int) (_stmt.getLong(_columnIndexOfXp));
          final String _tmpIdioma;
          if (_stmt.isNull(_columnIndexOfIdioma)) {
            _tmpIdioma = null;
          } else {
            _tmpIdioma = _stmt.getText(_columnIndexOfIdioma);
          }
          _result = new Player(_tmpId,_tmpName,_tmpLevel,_tmpXp,_tmpIdioma);
        } else {
          _result = null;
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
