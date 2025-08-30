package com.example.swiss.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.engine.SwissEngine;
import com.example.engine.model.Match;
import com.example.engine.model.MatchId;
import com.example.engine.model.MatchResult;
import com.example.engine.model.Round;
import com.example.engine.model.TournamentState;
import com.example.swiss.data.TournamentRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u00a8\u0006\u0006"}, d2 = {"roundComplete", "", "round", "Lcom/example/engine/model/Round;", "state", "Lcom/example/engine/model/TournamentState;", "app_debug"})
public final class TournamentViewModelKt {
    
    private static final boolean roundComplete(com.example.engine.model.Round round, com.example.engine.model.TournamentState state) {
        return false;
    }
}