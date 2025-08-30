package com.example.swiss.ui;

import android.content.Context;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.ExperimentalAnimationApi;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.example.engine.SwissEngine;
import com.example.swiss.export.CsvExporter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000n\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001ax\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\u001e\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u0015H\u0003\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0003\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001bH\u0003\u001a4\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u00072\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010!H\u0003\u001a(\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\t2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010%2\b\b\u0002\u0010&\u001a\u00020\'H\u0007\u001a\u0018\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a \u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\tH\u0002\u00a8\u00061"}, d2 = {"ExportButtons", "", "ui", "Lcom/example/swiss/ui/TournamentUiState;", "ExportMenuButton", "MatchEditor", "isLocked", "", "matchId", "", "roundIndex", "", "home", "away", "initHome", "initAway", "initDraws", "bestOf", "Lcom/example/engine/model/BestOf;", "allowDraws", "onChange", "Lkotlin/Function3;", "PlaceholderGraphic", "StandingsView", "state", "Lcom/example/engine/model/TournamentState;", "unlockedRounds", "", "Stepper", "label", "value", "enabled", "onDelta", "Lkotlin/Function1;", "TournamentScreen", "id", "onManualPair", "Lkotlin/Function0;", "vm", "Lcom/example/swiss/ui/TournamentViewModel;", "roundCompleteUi", "round", "Lcom/example/engine/model/Round;", "writeTextToUri", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "text", "app_debug"})
public final class TournamentScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.animation.ExperimentalAnimationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void TournamentScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onManualPair, @org.jetbrains.annotations.NotNull()
    com.example.swiss.ui.TournamentViewModel vm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MatchEditor(boolean isLocked, java.lang.String matchId, int roundIndex, java.lang.String home, java.lang.String away, int initHome, int initAway, int initDraws, com.example.engine.model.BestOf bestOf, boolean allowDraws, kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void Stepper(java.lang.String label, int value, boolean enabled, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onDelta) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExportButtons(com.example.swiss.ui.TournamentUiState ui) {
    }
    
    private static final void writeTextToUri(android.content.Context context, android.net.Uri uri, java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StandingsView(com.example.engine.model.TournamentState state, java.util.Set<java.lang.Integer> unlockedRounds) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExportMenuButton(com.example.swiss.ui.TournamentUiState ui) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaceholderGraphic() {
    }
    
    private static final boolean roundCompleteUi(com.example.engine.model.Round round, com.example.engine.model.TournamentState state) {
        return false;
    }
}