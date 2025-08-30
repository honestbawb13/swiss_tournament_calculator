package com.example.swiss.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TournamentDao_Impl implements TournamentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TournamentEntity> __insertionAdapterOfTournamentEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public TournamentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTournamentEntity = new EntityInsertionAdapter<TournamentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tournaments` (`id`,`name`,`bestOf`,`allowDraws`,`roundsPlanned`,`randomFirstRound`,`locked`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TournamentEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getBestOf() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBestOf());
        }
        final int _tmp = entity.getAllowDraws() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getRoundsPlanned());
        final int _tmp_1 = entity.getRandomFirstRound() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final int _tmp_2 = entity.getLocked() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tournaments WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final TournamentEntity entity, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTournamentEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object list(final Continuation<? super List<TournamentEntity>> arg0) {
    final String _sql = "SELECT * FROM tournaments ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TournamentEntity>>() {
      @Override
      @NonNull
      public List<TournamentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBestOf = CursorUtil.getColumnIndexOrThrow(_cursor, "bestOf");
          final int _cursorIndexOfAllowDraws = CursorUtil.getColumnIndexOrThrow(_cursor, "allowDraws");
          final int _cursorIndexOfRoundsPlanned = CursorUtil.getColumnIndexOrThrow(_cursor, "roundsPlanned");
          final int _cursorIndexOfRandomFirstRound = CursorUtil.getColumnIndexOrThrow(_cursor, "randomFirstRound");
          final int _cursorIndexOfLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "locked");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TournamentEntity> _result = new ArrayList<TournamentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TournamentEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBestOf;
            if (_cursor.isNull(_cursorIndexOfBestOf)) {
              _tmpBestOf = null;
            } else {
              _tmpBestOf = _cursor.getString(_cursorIndexOfBestOf);
            }
            final boolean _tmpAllowDraws;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAllowDraws);
            _tmpAllowDraws = _tmp != 0;
            final int _tmpRoundsPlanned;
            _tmpRoundsPlanned = _cursor.getInt(_cursorIndexOfRoundsPlanned);
            final boolean _tmpRandomFirstRound;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfRandomFirstRound);
            _tmpRandomFirstRound = _tmp_1 != 0;
            final boolean _tmpLocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfLocked);
            _tmpLocked = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TournamentEntity(_tmpId,_tmpName,_tmpBestOf,_tmpAllowDraws,_tmpRoundsPlanned,_tmpRandomFirstRound,_tmpLocked,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object get(final String id, final Continuation<? super TournamentEntity> arg1) {
    final String _sql = "SELECT * FROM tournaments WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TournamentEntity>() {
      @Override
      @Nullable
      public TournamentEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBestOf = CursorUtil.getColumnIndexOrThrow(_cursor, "bestOf");
          final int _cursorIndexOfAllowDraws = CursorUtil.getColumnIndexOrThrow(_cursor, "allowDraws");
          final int _cursorIndexOfRoundsPlanned = CursorUtil.getColumnIndexOrThrow(_cursor, "roundsPlanned");
          final int _cursorIndexOfRandomFirstRound = CursorUtil.getColumnIndexOrThrow(_cursor, "randomFirstRound");
          final int _cursorIndexOfLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "locked");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TournamentEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBestOf;
            if (_cursor.isNull(_cursorIndexOfBestOf)) {
              _tmpBestOf = null;
            } else {
              _tmpBestOf = _cursor.getString(_cursorIndexOfBestOf);
            }
            final boolean _tmpAllowDraws;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAllowDraws);
            _tmpAllowDraws = _tmp != 0;
            final int _tmpRoundsPlanned;
            _tmpRoundsPlanned = _cursor.getInt(_cursorIndexOfRoundsPlanned);
            final boolean _tmpRandomFirstRound;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfRandomFirstRound);
            _tmpRandomFirstRound = _tmp_1 != 0;
            final boolean _tmpLocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfLocked);
            _tmpLocked = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TournamentEntity(_tmpId,_tmpName,_tmpBestOf,_tmpAllowDraws,_tmpRoundsPlanned,_tmpRandomFirstRound,_tmpLocked,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
