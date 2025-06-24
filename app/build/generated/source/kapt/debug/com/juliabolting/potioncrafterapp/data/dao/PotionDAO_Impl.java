package com.juliabolting.potioncrafterapp.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.juliabolting.potioncrafterapp.data.model.Potion;
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
public final class PotionDAO_Impl implements PotionDAO {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Potion> __insertAdapterOfPotion;

  public PotionDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPotion = new EntityInsertAdapter<Potion>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Potion` (`id`,`nome`,`descricao`,`efeito`,`imagem`,`dataCriacao`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Potion entity) {
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
        if (entity.getEfeito() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getEfeito());
        }
        if (entity.getImagem() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getImagem());
        }
        statement.bindLong(6, entity.getDataCriacao());
      }
    };
  }

  @Override
  public Object insert(final Potion potion, final Continuation<? super Unit> $completion) {
    if (potion == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfPotion.insert(_connection, potion);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getAllPotions(final Continuation<? super List<Potion>> $completion) {
    final String _sql = "SELECT * FROM potion";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNome = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nome");
        final int _columnIndexOfDescricao = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descricao");
        final int _columnIndexOfEfeito = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "efeito");
        final int _columnIndexOfImagem = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagem");
        final int _columnIndexOfDataCriacao = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dataCriacao");
        final List<Potion> _result = new ArrayList<Potion>();
        while (_stmt.step()) {
          final Potion _item;
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
          final String _tmpEfeito;
          if (_stmt.isNull(_columnIndexOfEfeito)) {
            _tmpEfeito = null;
          } else {
            _tmpEfeito = _stmt.getText(_columnIndexOfEfeito);
          }
          final String _tmpImagem;
          if (_stmt.isNull(_columnIndexOfImagem)) {
            _tmpImagem = null;
          } else {
            _tmpImagem = _stmt.getText(_columnIndexOfImagem);
          }
          final long _tmpDataCriacao;
          _tmpDataCriacao = _stmt.getLong(_columnIndexOfDataCriacao);
          _item = new Potion(_tmpId,_tmpNome,_tmpDescricao,_tmpEfeito,_tmpImagem,_tmpDataCriacao);
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
