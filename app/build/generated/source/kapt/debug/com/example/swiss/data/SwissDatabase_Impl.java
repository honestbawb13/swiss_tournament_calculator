package com.example.swiss.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SwissDatabase_Impl extends SwissDatabase {
  private volatile TournamentDao _tournamentDao;

  private volatile PlayerDao _playerDao;

  private volatile MatchDao _matchDao;

  private volatile UnlockDao _unlockDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `tournaments` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `bestOf` TEXT NOT NULL, `allowDraws` INTEGER NOT NULL, `roundsPlanned` INTEGER NOT NULL, `randomFirstRound` INTEGER NOT NULL, `locked` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `players` (`id` TEXT NOT NULL, `tournamentId` TEXT NOT NULL, `name` TEXT NOT NULL, `seed` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_players_tournamentId` ON `players` (`tournamentId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `matches` (`id` TEXT NOT NULL, `tournamentId` TEXT NOT NULL, `roundIndex` INTEGER NOT NULL, `homePlayerId` TEXT, `awayPlayerId` TEXT, `homeGamesWon` INTEGER, `awayGamesWon` INTEGER, `draws` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_matches_tournamentId_roundIndex` ON `matches` (`tournamentId`, `roundIndex`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `unlock_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tournamentId` TEXT NOT NULL, `roundIndex` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_unlock_events_tournamentId` ON `unlock_events` (`tournamentId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9b732dc593d7349b1cfd6a65e064cc19')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tournaments`");
        db.execSQL("DROP TABLE IF EXISTS `players`");
        db.execSQL("DROP TABLE IF EXISTS `matches`");
        db.execSQL("DROP TABLE IF EXISTS `unlock_events`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTournaments = new HashMap<String, TableInfo.Column>(8);
        _columnsTournaments.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("bestOf", new TableInfo.Column("bestOf", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("allowDraws", new TableInfo.Column("allowDraws", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("roundsPlanned", new TableInfo.Column("roundsPlanned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("randomFirstRound", new TableInfo.Column("randomFirstRound", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("locked", new TableInfo.Column("locked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTournaments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTournaments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTournaments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTournaments = new TableInfo("tournaments", _columnsTournaments, _foreignKeysTournaments, _indicesTournaments);
        final TableInfo _existingTournaments = TableInfo.read(db, "tournaments");
        if (!_infoTournaments.equals(_existingTournaments)) {
          return new RoomOpenHelper.ValidationResult(false, "tournaments(com.example.swiss.data.TournamentEntity).\n"
                  + " Expected:\n" + _infoTournaments + "\n"
                  + " Found:\n" + _existingTournaments);
        }
        final HashMap<String, TableInfo.Column> _columnsPlayers = new HashMap<String, TableInfo.Column>(4);
        _columnsPlayers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("tournamentId", new TableInfo.Column("tournamentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("seed", new TableInfo.Column("seed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayers = new HashSet<TableInfo.Index>(1);
        _indicesPlayers.add(new TableInfo.Index("index_players_tournamentId", false, Arrays.asList("tournamentId"), Arrays.asList("ASC")));
        final TableInfo _infoPlayers = new TableInfo("players", _columnsPlayers, _foreignKeysPlayers, _indicesPlayers);
        final TableInfo _existingPlayers = TableInfo.read(db, "players");
        if (!_infoPlayers.equals(_existingPlayers)) {
          return new RoomOpenHelper.ValidationResult(false, "players(com.example.swiss.data.PlayerEntity).\n"
                  + " Expected:\n" + _infoPlayers + "\n"
                  + " Found:\n" + _existingPlayers);
        }
        final HashMap<String, TableInfo.Column> _columnsMatches = new HashMap<String, TableInfo.Column>(8);
        _columnsMatches.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("tournamentId", new TableInfo.Column("tournamentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("roundIndex", new TableInfo.Column("roundIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("homePlayerId", new TableInfo.Column("homePlayerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("awayPlayerId", new TableInfo.Column("awayPlayerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("homeGamesWon", new TableInfo.Column("homeGamesWon", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("awayGamesWon", new TableInfo.Column("awayGamesWon", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatches.put("draws", new TableInfo.Column("draws", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMatches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMatches = new HashSet<TableInfo.Index>(1);
        _indicesMatches.add(new TableInfo.Index("index_matches_tournamentId_roundIndex", false, Arrays.asList("tournamentId", "roundIndex"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoMatches = new TableInfo("matches", _columnsMatches, _foreignKeysMatches, _indicesMatches);
        final TableInfo _existingMatches = TableInfo.read(db, "matches");
        if (!_infoMatches.equals(_existingMatches)) {
          return new RoomOpenHelper.ValidationResult(false, "matches(com.example.swiss.data.MatchEntity).\n"
                  + " Expected:\n" + _infoMatches + "\n"
                  + " Found:\n" + _existingMatches);
        }
        final HashMap<String, TableInfo.Column> _columnsUnlockEvents = new HashMap<String, TableInfo.Column>(4);
        _columnsUnlockEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("tournamentId", new TableInfo.Column("tournamentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("roundIndex", new TableInfo.Column("roundIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUnlockEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUnlockEvents = new HashSet<TableInfo.Index>(1);
        _indicesUnlockEvents.add(new TableInfo.Index("index_unlock_events_tournamentId", false, Arrays.asList("tournamentId"), Arrays.asList("ASC")));
        final TableInfo _infoUnlockEvents = new TableInfo("unlock_events", _columnsUnlockEvents, _foreignKeysUnlockEvents, _indicesUnlockEvents);
        final TableInfo _existingUnlockEvents = TableInfo.read(db, "unlock_events");
        if (!_infoUnlockEvents.equals(_existingUnlockEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "unlock_events(com.example.swiss.data.UnlockEventEntity).\n"
                  + " Expected:\n" + _infoUnlockEvents + "\n"
                  + " Found:\n" + _existingUnlockEvents);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9b732dc593d7349b1cfd6a65e064cc19", "4f897dc0009c372a6263596f159aefed");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tournaments","players","matches","unlock_events");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tournaments`");
      _db.execSQL("DELETE FROM `players`");
      _db.execSQL("DELETE FROM `matches`");
      _db.execSQL("DELETE FROM `unlock_events`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TournamentDao.class, TournamentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlayerDao.class, PlayerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MatchDao.class, MatchDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UnlockDao.class, UnlockDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
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
  public TournamentDao tournaments() {
    if (_tournamentDao != null) {
      return _tournamentDao;
    } else {
      synchronized(this) {
        if(_tournamentDao == null) {
          _tournamentDao = new TournamentDao_Impl(this);
        }
        return _tournamentDao;
      }
    }
  }

  @Override
  public PlayerDao players() {
    if (_playerDao != null) {
      return _playerDao;
    } else {
      synchronized(this) {
        if(_playerDao == null) {
          _playerDao = new PlayerDao_Impl(this);
        }
        return _playerDao;
      }
    }
  }

  @Override
  public MatchDao matches() {
    if (_matchDao != null) {
      return _matchDao;
    } else {
      synchronized(this) {
        if(_matchDao == null) {
          _matchDao = new MatchDao_Impl(this);
        }
        return _matchDao;
      }
    }
  }

  @Override
  public UnlockDao unlocks() {
    if (_unlockDao != null) {
      return _unlockDao;
    } else {
      synchronized(this) {
        if(_unlockDao == null) {
          _unlockDao = new UnlockDao_Impl(this);
        }
        return _unlockDao;
      }
    }
  }
}
