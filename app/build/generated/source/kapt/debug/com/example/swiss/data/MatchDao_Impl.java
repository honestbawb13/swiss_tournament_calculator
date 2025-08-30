package com.example.swiss.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
import java.lang.Integer;
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
public final class MatchDao_Impl implements MatchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MatchEntity> __insertionAdapterOfMatchEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByTournament;

  public MatchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMatchEntity = new EntityInsertionAdapter<MatchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `matches` (`id`,`tournamentId`,`roundIndex`,`homePlayerId`,`awayPlayerId`,`homeGamesWon`,`awayGamesWon`,`draws`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MatchEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTournamentId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTournamentId());
        }
        statement.bindLong(3, entity.getRoundIndex());
        if (entity.getHomePlayerId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getHomePlayerId());
        }
        if (entity.getAwayPlayerId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAwayPlayerId());
        }
        if (entity.getHomeGamesWon() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getHomeGamesWon());
        }
        if (entity.getAwayGamesWon() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getAwayGamesWon());
        }
        if (entity.getDraws() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getDraws());
        }
      }
    };
    this.__preparedStmtOfDeleteByTournament = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM matches WHERE tournamentId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<MatchEntity> matches, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMatchEntity.insert(matches);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteByTournament(final String tid, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByTournament.acquire();
        int _argIndex = 1;
        if (tid == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, tid);
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
          __preparedStmtOfDeleteByTournament.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object listByTournament(final String tid,
      final Continuation<? super List<MatchEntity>> arg1) {
    final String _sql = "SELECT * FROM matches WHERE tournamentId = ? ORDER BY roundIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tid);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MatchEntity>>() {
      @Override
      @NonNull
      public List<MatchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTournamentId = CursorUtil.getColumnIndexOrThrow(_cursor, "tournamentId");
          final int _cursorIndexOfRoundIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "roundIndex");
          final int _cursorIndexOfHomePlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "homePlayerId");
          final int _cursorIndexOfAwayPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "awayPlayerId");
          final int _cursorIndexOfHomeGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "homeGamesWon");
          final int _cursorIndexOfAwayGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "awayGamesWon");
          final int _cursorIndexOfDraws = CursorUtil.getColumnIndexOrThrow(_cursor, "draws");
          final List<MatchEntity> _result = new ArrayList<MatchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MatchEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTournamentId;
            if (_cursor.isNull(_cursorIndexOfTournamentId)) {
              _tmpTournamentId = null;
            } else {
              _tmpTournamentId = _cursor.getString(_cursorIndexOfTournamentId);
            }
            final int _tmpRoundIndex;
            _tmpRoundIndex = _cursor.getInt(_cursorIndexOfRoundIndex);
            final String _tmpHomePlayerId;
            if (_cursor.isNull(_cursorIndexOfHomePlayerId)) {
              _tmpHomePlayerId = null;
            } else {
              _tmpHomePlayerId = _cursor.getString(_cursorIndexOfHomePlayerId);
            }
            final String _tmpAwayPlayerId;
            if (_cursor.isNull(_cursorIndexOfAwayPlayerId)) {
              _tmpAwayPlayerId = null;
            } else {
              _tmpAwayPlayerId = _cursor.getString(_cursorIndexOfAwayPlayerId);
            }
            final Integer _tmpHomeGamesWon;
            if (_cursor.isNull(_cursorIndexOfHomeGamesWon)) {
              _tmpHomeGamesWon = null;
            } else {
              _tmpHomeGamesWon = _cursor.getInt(_cursorIndexOfHomeGamesWon);
            }
            final Integer _tmpAwayGamesWon;
            if (_cursor.isNull(_cursorIndexOfAwayGamesWon)) {
              _tmpAwayGamesWon = null;
            } else {
              _tmpAwayGamesWon = _cursor.getInt(_cursorIndexOfAwayGamesWon);
            }
            final Integer _tmpDraws;
            if (_cursor.isNull(_cursorIndexOfDraws)) {
              _tmpDraws = null;
            } else {
              _tmpDraws = _cursor.getInt(_cursorIndexOfDraws);
            }
            _item = new MatchEntity(_tmpId,_tmpTournamentId,_tmpRoundIndex,_tmpHomePlayerId,_tmpAwayPlayerId,_tmpHomeGamesWon,_tmpAwayGamesWon,_tmpDraws);
            _result.add(_item);
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
