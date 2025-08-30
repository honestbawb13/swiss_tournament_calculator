package com.example.swiss.export;

import com.example.engine.SwissEngine;
import com.example.engine.model.TournamentState;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J\b\u0010\n\u001a\u00020\u0006H\u0002J\u0016\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/swiss/export/CsvExporter;", "", "engine", "Lcom/example/engine/SwissEngine;", "(Lcom/example/engine/SwissEngine;)V", "matchesCsv", "", "state", "Lcom/example/engine/model/TournamentState;", "tournamentName", "nowIso", "standingsCsv", "app_debug"})
public final class CsvExporter {
    @org.jetbrains.annotations.NotNull()
    private final com.example.engine.SwissEngine engine = null;
    
    public CsvExporter(@org.jetbrains.annotations.NotNull()
    com.example.engine.SwissEngine engine) {
        super();
    }
    
    private final java.lang.String nowIso() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String standingsCsv(@org.jetbrains.annotations.NotNull()
    com.example.engine.model.TournamentState state, @org.jetbrains.annotations.NotNull()
    java.lang.String tournamentName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String matchesCsv(@org.jetbrains.annotations.NotNull()
    com.example.engine.model.TournamentState state, @org.jetbrains.annotations.NotNull()
    java.lang.String tournamentName) {
        return null;
    }
    
    public CsvExporter() {
        super();
    }
}